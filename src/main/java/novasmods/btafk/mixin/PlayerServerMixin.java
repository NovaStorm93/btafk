package novasmods.btafk.mixin;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.net.packet.PacketEntityNickname;
import net.minecraft.core.net.packet.PacketUpdatePlayerState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import net.minecraft.server.world.ServerPlayerController;
import novasmods.btafk.BTAFK;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.events.EventBlocksMined;
import novasmods.btafk.interfaces.IPlayerUtilities;
import novasmods.btafk.interfaces.ITicksIdle;
import novasmods.btafk.melonutilities.MelonUtilitiesAPI;

@Mixin(value = PlayerServer.class, remap = false)
public class PlayerServerMixin implements ITicksIdle,IPlayerUtilities {

    
    
    @Unique
    public int ticksIdle = 0;
    @Unique
    public boolean flaggedAFK = false;
    @Unique
    public HashMap<Block<?>,EventBlocksMined> blocksToMine = new HashMap<Block<?>,EventBlocksMined>();
    
    
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
        MinecraftServer server = MinecraftServer.getInstance();
        PlayerList playerList = server.playerList;
        
        String afkMessage = BTAFKConfigs.becomeAfkMessage;
        afkMessage = formatStringWithPlayerInformation(afkMessage);
        
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
            
            String noLongerAfkMessage = BTAFKConfigs.noLongerAfkMessage;
            noLongerAfkMessage = formatStringWithPlayerInformation(noLongerAfkMessage);
            playerList.sendEncryptedChatToAllPlayers(noLongerAfkMessage);
            MelonUtilitiesAPI.getInstance().discordSendPlayerNotAfk(self.username);
            playerList.updatePlayerProfile(self.username, self.getDisplayName(),self.uuid,self.score, self.chatColor, true, self.isOperator());
    }
    
    
    
    
    public String formatStringWithPlayerInformation(String input){
        PlayerServer self = (PlayerServer)(Object)this;
        input = input.replaceAll("<playercolor>", TextFormatting.get(self.chatColor).toString());
        input = input.replaceAll("<pc>",TextFormatting.get(self.chatColor).toString());
        
        if(isModAuthor()){
                input = input.replaceAll("<player>", TextFormatting.YELLOW + "⭐" + self.getDisplayName() + TextFormatting.RESET);
                input = input.replaceAll("<player:raw>", "⭐" + getRawDisplayName());
            }
            else{
                input = input.replaceAll("<player>",self.getDisplayName() + TextFormatting.RESET);
                input = input.replaceAll("<player:raw>", getRawDisplayName());
            }
        input = input.replaceAll("<server>", BTAFKConfigs.serverName);
        
        return input;
        
        
    }
    
    
    
    
    
    public void trackBlocksBrokenByPlayer(Block<?> block){
        
        
        
    }
    
    
    public String getRawDisplayName(){
        return ((PlayerServer)(Object)this).getDisplayName().substring(2);
    }

    @Override
    public void onBlockBrokenByPlayer(Block<?> block) {
        
        
        
        
    }

    @Override
    public void onBlockPlacedByPlayer(Block<?> block) {
        
    }
    
    
    
    
    
}
