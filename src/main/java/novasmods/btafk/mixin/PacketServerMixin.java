package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

@Mixin(value = Packet.class, remap = false)
public abstract class PacketServerMixin{
    @Inject(method = "handlePacket(Lnet/minecraft/core/net/handler/PacketHandler;)V", at = @At("HEAD"))
    public void handlePacket(CallbackInfo ci, PacketHandler packetHandler) {
        
        System.out.println("You can stop being a cuck now");
        
        

    }

}
