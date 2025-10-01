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
import novasmods.btafk.BTAFK;
import novasmods.btafk.interfaces.ITicksIdle;

@Mixin(value = PlayerServer.class, remap = false)
public class PlayerServerMixin implements ITicksIdle {

    @Unique
    public int ticksIdle = 0;
    @Unique
    public boolean flaggedAFK = false;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {
        PlayerServer self = (PlayerServer) (Object) this;
        ((ITicksIdle) this).incrementTicksIdle();

        if (ticksIdle >= BTAFK.TICKS_UNTIL_AFK && !flaggedAFK) {
            MinecraftServer.getInstance().playerList
                    .sendEncryptedChatToAllPlayers(self.getDisplayName() + TextFormatting.RESET + " is now AFK");
            flaggedAFK = true;
            MinecraftServer.getInstance().playerList.updatePlayerProfile(self.username, self.getDisplayName(),
                    self.uuid, self.score, self.chatColor, true, self.isOperator());
        }

    }

    @Inject(method = "getDisplayName()Ljava/lang/String;", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private String modifyAfkString(CallbackInfoReturnable ci, String local) {
        PlayerServer self = (PlayerServer) (Object) this;

        String s = TextFormatting.get(self.chatColor) + local;

        if (flaggedAFK) {
            s += TextFormatting.GRAY + "[AFK]";
        }

        return s;

    }

    @Override
    public void incrementTicksIdle() {
        if (ticksIdle + 1 > BTAFK.TICKS_UNTIL_AFK) {
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
            flaggedAFK = false;
            PlayerServer self = (PlayerServer) (Object) this;
            MinecraftServer.getInstance().playerList
                    .sendEncryptedChatToAllPlayers(self.getDisplayName() + TextFormatting.RESET + " is no longer AFK");
            MinecraftServer.getInstance().playerList.updatePlayerProfile(self.username, self.getDisplayName(),
                    self.uuid,
                    self.score, self.chatColor, true, self.isOperator());
        }
        flaggedAFK = false;
        ticksIdle = 0;
        
        

        return;
    }

}
