package novasmods.btafk.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.nbt.tags.CompoundTag;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.ChatEmotes;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.world.save.SaveHandlerServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFK;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.interfaces.IEvent;
import novasmods.btafk.melonutilities.MelonUtilitiesAPI;

@Mixin(value=SaveHandlerServer.class,remap = false)
public class SaveHandlerServerMixin implements IEvent{
    
    
    @Inject(method = "load", at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILHARD)
    public void onPlayerLoad(Player player,CallbackInfo ci, CompoundTag tag){
        if(tag == null) notifyNewPlayerJoined(player);
        
        
    }
    

    private void notifyNewPlayerJoined(Player player){
        MelonUtilitiesAPI.getInstance().discordSendPlayerFirstJoin(player.username);
        List args = new ArrayList<>();
        args.add(player);
        
        
        BTAFK.eventScheduler.scheduleEvent(this,1, args);
        
        
        
        
        
        
        
        
        
    }
    @Override
    public void runEvent(List args) {
        Player player = (Player) args.get(0);
        PlayerList playerList = MinecraftServer.getInstance().playerList;
        String username = player.username;
        
        String welcomeMessage;
        
        if(BTAFK.isModAuthor(player)){
            welcomeMessage = String.format(BTAFKConfigs.welcomeMessage,TextFormatting.PURPLE + "⭐" + username + TextFormatting.RESET);
        }
        else{
            welcomeMessage = String.format(BTAFKConfigs.welcomeMessage,username + TextFormatting.RESET);   
        }
        
        playerList.sendEncryptedChatToAllPlayers(welcomeMessage);
        
        
        
        
    }
    
    
}
