package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.server.world.ServerPlayerController;

@Mixin(value=ServerPlayerController.class,remap = false)
public class ServerPlayerControllerMixin {
    
}
