package novasmods.btafk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.PlayerServer;
import novasmods.btafk.mixin.mixininterfaces.ITicksIdle;

@Mixin(value=PlayerServer.class,remap = false)
public class PlayerServerMixin implements ITicksIdle{
    
    @Unique
    public int ticksIdle = 0;
    
    
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci){
        ((PlayerServerMixin) (Object) this).incrementTicksIdle();
    }
    
    @Inject(method = "getDisplayName()Ljava/lang/String;",at = @At("RETURN"),locals = LocalCapture.CAPTURE_FAILHARD)
    private String modifyAfkString(CallbackInfoReturnable ci, String local){
        PlayerServer self = (PlayerServer)(Object) this;
        
        
        String s = TextFormatting.get(self.chatColor) + local;
        
        if(ticksIdle > 200){
            s += TextFormatting.GRAY + "[AFK]";
        }
        
        
        
        
        return s;
        
        
        
    }
    
    
    
    @Override
    public void incrementTicksIdle(){
        if(ticksIdle+1 < ticksIdle){
            return;
        }
        ticksIdle++;
        
        
        
    }
    @Override
    public void resetTicksIdle(){
        ticksIdle = 0;
        return;
    }
    
    
    
    
}
