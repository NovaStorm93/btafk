package novasmods.btafk.events;

import net.minecraft.core.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.interfaces.IPlayerUtilities;

public class EventSendReturningMessage extends Event {

    
    Player player;
    
    public EventSendReturningMessage(Player player,int tickDelay) {
        super(tickDelay);
        this.player = player;
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void runEvent() {
        if(player == null) return;
        
        MinecraftServer mc = MinecraftServer.getInstance();
        PlayerList playerList = mc.playerList;
        IPlayerUtilities playerUtils = (IPlayerUtilities)player;
        
        
        String returningMessage = BTAFKConfigs.returningMessage;
        returningMessage = playerUtils.formatStringWithPlayerInformation(returningMessage);
        player.sendMessage(returningMessage);
    }
    
}
