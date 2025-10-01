package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;


@Mixin(value = PacketHandlerServer.class,remap = false)
public interface PacketHandlerServerAcessor {
    @Accessor
    PlayerServer getPlayerEntity();
    
}
