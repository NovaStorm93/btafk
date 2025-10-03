package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;
import novasmods.btafk.BTAFK;

@Mixin(value=MinecraftServer.class,remap = false)
public class MinecraftServerMixin {
    @Inject(method = "doTick",at = @At("HEAD"))
    public void onPreTick(CallbackInfo ci){
        BTAFK.onServerPreTick();
    }
    
    
    
    
}
