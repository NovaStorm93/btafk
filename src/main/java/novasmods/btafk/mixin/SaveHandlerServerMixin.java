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
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFK;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.events.EventSendMotd;
import novasmods.btafk.events.EventSendReturningMessage;
import novasmods.btafk.events.EventSendWelcomeMessage;
import novasmods.btafk.melonutilities.MelonUtilitiesAPI;

@Mixin(value=SaveHandlerServer.class,remap = false)
public class SaveHandlerServerMixin{
    
    
    @Inject(method = "load", at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILHARD)
    public void onPlayerLoad(Player player,CallbackInfo ci, CompoundTag tag){
        if(tag == null){
            notifyNewPlayerJoined(player);
        }
        else{
            notifyReturningPlayer(player);
        }
        sendMOTD(player);
        
    }
    
    private void sendMOTD(Player player){
        if(!BTAFKConfigs.motdGlobalEnabled) return;
        BTAFK.eventScheduler.scheduleEvent(new EventSendMotd(player,BTAFKConfigs.motdTickDelay));
    }
    
    
    private void notifyNewPlayerJoined(Player player){
        if(!BTAFKConfigs.welcomeGlobalEnabled) return;
        
        MelonUtilitiesAPI.getInstance().discordSendPlayerFirstJoin(player.username);
        BTAFK.eventScheduler.scheduleEvent(new EventSendWelcomeMessage(player,BTAFKConfigs.welcomeTickDelay));
    }
    private void notifyReturningPlayer(Player player){
        if(!BTAFKConfigs.returningGlobalEnabled) return;
        BTAFK.eventScheduler.scheduleEvent(new EventSendReturningMessage(player, BTAFKConfigs.returningTickDelay));
        
        
    }
    
}
