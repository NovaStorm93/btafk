package novasmods.btafk.events;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFK;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.interfaces.IPlayerUtilities;

public class EventSendWelcomeMessage extends Event{
    private Player player;
    
    
    
    public EventSendWelcomeMessage(Player player, int tickDelay){
        super(tickDelay);
        this.player = player;
    }
    
    
    protected void runEvent(){
        if(player == null){
            return;
        }
        
        MinecraftServer mc = MinecraftServer.getInstance();
        PlayerList playerList = mc.playerList;
        IPlayerUtilities playerUtils = (IPlayerUtilities)player;
        
        
        String welcomeMessage = BTAFKConfigs.welcomeMessage;
        welcomeMessage = playerUtils.formatStringWithPlayerInformation(welcomeMessage);
        playerList.sendEncryptedChatToAllPlayers(welcomeMessage);
        
        
        
    }
}
