package novasmods.btafk.interfaces;

import net.minecraft.core.block.Block;

public interface IPlayerUtilities {
    
    // public void sendWelcomeMessage();
    // public void sendMotd();
    public String formatStringWithPlayerInformation(String in);
    public void onBlockBrokenByPlayer(Block<?> block);
    public void onBlockPlacedByPlayer(Block<?> block);
    
    
}
