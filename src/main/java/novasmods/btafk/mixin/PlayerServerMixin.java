package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.PacketEntityNickname;
import net.minecraft.core.net.packet.PacketUpdatePlayerState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFK;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.interfaces.ITicksIdle;
import novasmods.btafk.melonutilities.MelonUtilitiesAPI;

@Mixin(value = PlayerServer.class, remap = false)
public class PlayerServerMixin implements ITicksIdle {

    @Unique
    public int ticksIdle = 0;
    @Unique
    public boolean flaggedAFK = false;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        if(!BTAFKConfigs.afkGlobalEnabled) return;
        PlayerServer self = (PlayerServer) (Object) this;
        ((ITicksIdle) this).incrementTicksIdle();

        if (ticksIdle >= BTAFKConfigs.ticksUntilMarkedAfk && !flaggedAFK) {
            onPlayerAfk();
        }

    }

    @Inject(method = "getDisplayName()Ljava/lang/String;", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private String modifyAfkString(CallbackInfoReturnable ci, String local) {
        PlayerServer self = (PlayerServer) (Object) this;

        String s = TextFormatting.get(self.chatColor) + local;

        if (flaggedAFK) {
            if(isModAuthor()){
                s += TextFormatting.PURPLE;
            }
            else{
                s += TextFormatting.GRAY;
            }
            s += "[AFK]";
        }

        return s;

    }

    @Override
    public void incrementTicksIdle() {
        if (ticksIdle + 1 > BTAFKConfigs.ticksUntilMarkedAfk || !BTAFKConfigs.afkGlobalEnabled) {
            return;
        }
        ticksIdle++;
        if(BTAFK.DEBUG_MODE){
            MinecraftServer.getInstance().playerList.sendEncryptedChatToAllPlayers(ticksIdle + "");
        }
        
        
    }

    @Override
    public void resetTicksIdle() {
        
        if (flaggedAFK) {
            onPlayerNoLongerAfk();
        }
        flaggedAFK = false;
        ticksIdle = 0;
        
        

        return;
    }

    
    private boolean isModAuthor(){
        return BTAFK.isModAuthor((PlayerServer)(Object)this);
        
    }
    
    
    
    private void onPlayerAfk(){
        if(!BTAFKConfigs.afkGlobalEnabled) return;
        
        PlayerServer self = (PlayerServer) (Object) this;
        String afkMessage;
        MinecraftServer server = MinecraftServer.getInstance();
        PlayerList playerList = server.playerList;
        
        if(isModAuthor()){
            afkMessage = String.format(BTAFKConfigs.becomeAfkMessage,TextFormatting.PURPLE + "⭐" + self.username + TextFormatting.RESET);
        }
        else{
            afkMessage = String.format(BTAFKConfigs.becomeAfkMessage,self.username + TextFormatting.RESET);
        }
        
        MelonUtilitiesAPI.getInstance().discordSendPlayerNowAfk(self.username);
        playerList.sendEncryptedChatToAllPlayers(afkMessage);
        
        flaggedAFK = true;
        playerList.updatePlayerProfile(self.username, self.getDisplayName(),self.uuid, self.score, self.chatColor, true, self.isOperator());
    }
    
    private void onPlayerNoLongerAfk(){
        if(!BTAFKConfigs.afkGlobalEnabled) return;
        
        flaggedAFK = false;
            PlayerServer self = (PlayerServer) (Object) this;
            MinecraftServer server = MinecraftServer.getInstance();
            PlayerList playerList = server.playerList;
            
            String noLongerAfkMessage;
            if(isModAuthor()){
                noLongerAfkMessage = String.format(BTAFKConfigs.noLongerAfkMessage,TextFormatting.PURPLE + "⭐" + self.getDisplayName() + TextFormatting.RESET);
            }
            else{
                noLongerAfkMessage = String.format(BTAFKConfigs.noLongerAfkMessage,self.getDisplayName() + TextFormatting.RESET);
            }
            
            playerList.sendEncryptedChatToAllPlayers(noLongerAfkMessage);
            MelonUtilitiesAPI.getInstance().discordSendPlayerNotAfk(self.username);
            playerList.updatePlayerProfile(self.username, self.getDisplayName(),self.uuid,self.score, self.chatColor, true, self.isOperator());
    }
    
    
    
}
