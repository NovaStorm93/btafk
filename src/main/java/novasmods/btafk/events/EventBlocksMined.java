package novasmods.btafk.events;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.PlayerList;
import novasmods.btafk.BTAFKConfigs;

public class EventBlocksMined extends Event {

    public int numBlocksMined;
    public Block<?> block;
    public Player player;
    
    
    public void incrementBlocksMined(){
        resetTickDelay();
    }
    
    private void resetTickDelay(){
        this.tickDelay = BTAFKConfigs.blockMinerTickDelay;
    }
    
    public EventBlocksMined(Player player,Block<?> block, int tickDelay) {
        super(tickDelay);
        this.block = block;
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void runEvent() {
        MinecraftServer mc = MinecraftServer.getInstance();
        PlayerList playerList = mc.playerList;
        
        
        
        playerList.sendEncryptedChatToAllPlayers("");
        
        
        
        
    }
    
}
