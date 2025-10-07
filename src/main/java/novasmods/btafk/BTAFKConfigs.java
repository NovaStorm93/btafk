package novasmods.btafk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import net.fabricmc.loader.api.FabricLoader;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class BTAFKConfigs {
    public static final String CONFIG_FILE_NAME = BTAFK.MOD_ID + ".cfg";
    public static final String CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir() + BTAFK.MOD_ID + "/" + CONFIG_FILE_NAME;
    public static boolean afkGlobalEnabled;
    public static int ticksUntilMarkedAfk;
    public static String becomeAfkMessage;
    public static String noLongerAfkMessage;
    public static boolean afkSendDiscordMessages;
    
    public static boolean welcomeGlobalEnabled;
    public static String welcomeMessage;
    public static boolean welcomeSendDiscordMessages;
    
    
    
    public static TomlConfigHandler tomlConfigHandler;
    
    
    
    public static void onModLoaded(){
        Toml toml = new Toml("BTAFK configuration.");
        
        
        toml.addCategory("AFK settings","afk");
        toml.addEntry("afk.global_enabled","Enables/Disables all AFK-Related functionality. Default:true",true);
        toml.addEntry("afk.ticksUntilMarkedAfk","How many ticks idle until the server flags a player as afk. Minecraft runs at 20 ticks per second. Default:60000 (5 Minutes)", 60000);
        toml.addEntry("afk.becomeAfkMessage","The message sent to all players when a player becomes afk. \"%s\" is replaced with the player's username. Default:\"%s is now AFK.\"","%s is now AFK.");
        toml.addEntry("afk.noLongerAfkMessage", "The message sent to all players when a player is no longer afk. \"%s\" is replaced with the player's username. Default:\"%s is no longer AFK.\"", "%s is no longer AFK.");
        toml.addEntry("afk.sendDiscordMessages","Enables/Disables sending discord messages via MelonUtilities when a player becomes/is no longer afk. Does nothing if MelonUtilities is not installed. Default:true",true);
        
        toml.addCategory("Welcome Message settings.","welcome");
        toml.addEntry("welcome.global_enabled","Enables/Disables all Welcome Message-Related functionality. Default:true",true);
        toml.addEntry("welcome.welcomeMessage", "The message sent to all players when a player is new to the server. \"%s\" is replaced with the player's username. Default: \"§4%s§3 is new to the server!\"","§4%s§3 is new to the server!");
        toml.addEntry("welcome.sendDiscordMessages","Enables/Disables sending discord messages via MelonUtilities when a player is new to the server. Does nothing if MelonUtilities is not installed. Default:true",true);
        
        tomlConfigHandler = new TomlConfigHandler(BTAFK.MOD_ID, toml);
        afkGlobalEnabled = tomlConfigHandler.getBoolean("afk.global_enabled");
        
        
        ticksUntilMarkedAfk = tomlConfigHandler.getInt("afk.ticksUntilMarkedAfk");
        if(BTAFK.DEBUG_MODE){
            ticksUntilMarkedAfk = 100;
        }
        
        
        becomeAfkMessage = tomlConfigHandler.getString("afk.becomeAfkMessage");
        noLongerAfkMessage = tomlConfigHandler.getString("afk.noLongerAfkMessage");
        afkSendDiscordMessages = tomlConfigHandler.getBoolean("afk.sendDiscordMessages");
        
        welcomeGlobalEnabled = tomlConfigHandler.getBoolean("welcome.global_enabled");
        welcomeMessage = tomlConfigHandler.getString("welcome.welcomeMessage");
        welcomeSendDiscordMessages = tomlConfigHandler.getBoolean("welcome.sendDiscordMessages");
        
        
        
        
    }
    
    

    
    
}
