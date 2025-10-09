package novasmods.btafk.events;

import net.minecraft.core.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFKConfigs;
import novasmods.btafk.interfaces.IPlayerUtilities;

public class EventSendMotd extends Event {

    private Player player;
    
    public EventSendMotd(Player player,int tickDelay) {
        super(tickDelay);
        this.player = player;
    }

    @Override
    protected void runEvent() {
        if(player == null) return;
        MinecraftServer mc = MinecraftServer.getInstance();
        PlayerList playerList = mc.playerList;
        IPlayerUtilities playerUtils = (IPlayerUtilities)player;
        
        
        String motdMessage = BTAFKConfigs.motdMessage;
        motdMessage = playerUtils.formatStringWithPlayerInformation(motdMessage);
        player.sendMessage(motdMessage);
    }
    
}
