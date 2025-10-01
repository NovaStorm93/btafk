package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketChat;
import net.minecraft.core.net.packet.PacketContainerClick;
import net.minecraft.core.net.packet.PacketContainerClose;
import net.minecraft.core.net.packet.PacketInteract;
import net.minecraft.core.net.packet.PacketMovePlayer;
import net.minecraft.core.net.packet.PacketPlayerAction;
import net.minecraft.core.net.packet.PacketRequestCommandManager;
import net.minecraft.core.net.packet.PacketSignUpdate;
import net.minecraft.core.net.packet.PacketUpdatePlayerState;
import net.minecraft.core.net.packet.PacketUseOrPlaceItemStack;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;


@Mixin(value = NetworkManager.class,remap = false)
public class NetworkManagerMixin {
    
    @Redirect(method = "processReadPackets()V", at = @At(value="INVOKE",target="Lnet/minecraft/core/net/packet/Packet;handlePacket(Lnet/minecraft/core/net/handler/PacketHandler;)V"))
    private void onProcessReadPackets(Packet packet,PacketHandler packetHandler){
        if (packet instanceof PacketMovePlayer.Rot ||
                packet instanceof PacketMovePlayer.Pos ||
                packet instanceof PacketMovePlayer.PosRot ||
                packet instanceof PacketRequestCommandManager ||
                packet instanceof PacketUpdatePlayerState ||
                packet instanceof PacketPlayerAction ||
                packet instanceof PacketUseOrPlaceItemStack ||
                packet instanceof PacketContainerClose ||
                packet instanceof PacketContainerClick ||
                packet instanceof PacketInteract ||
                packet instanceof PacketSignUpdate ||
                packet instanceof PacketChat
        ) {
            
            
            PlayerServer playerServer = ((PacketHandlerServerAcessor) (PacketHandlerServer) packetHandler).getPlayerEntity();
            if(playerServer != null){
                // ((ITicksIdle)playerServer).resetTicksIdle();
            }
        }
        
        
        
        
        packet.handlePacket(packetHandler);
        
        
    }
    
}
