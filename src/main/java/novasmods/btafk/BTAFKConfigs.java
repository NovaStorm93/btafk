package novasmods.btafk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.net.command.TextFormatting;
import novasmods.btafk.melonutilities.MelonUtilitiesAPI;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public class BTAFKConfigs {
    public static final String CONFIG_FILE_NAME = BTAFK.MOD_ID + ".cfg";
    public static final String CONFIG_FILE_PATH = FabricLoader.getInstance().getConfigDir() + BTAFK.MOD_ID + "/" + CONFIG_FILE_NAME;
    
    public static String serverName;
    
    
    // AFK variables
    public static boolean afkGlobalEnabled;
    public static int ticksUntilMarkedAfk;
    public static String becomeAfkMessage;
    public static String noLongerAfkMessage;
    public static boolean afkSendDiscordMessages;
    
    
    // Welcome variables
    public static boolean welcomeGlobalEnabled;
    public static String welcomeMessage;
    public static boolean welcomeSendDiscordMessages;
    public static int welcomeTickDelay;
    
    
    // MOTD variables
    public static boolean motdGlobalEnabled;
    public static String motdMessage;
    public static int motdTickDelay;
    
    
    // Welcome Back variables
    public static boolean returningGlobalEnabled;
    public static String returningMessage;
    public static int returningTickDelay;
    
    // Block Miner variables;
    public static int blockMinerTickDelay;
    
    
    
    public static TomlConfigHandler tomlConfigHandler;
    
    
    
    public static void onModLoaded(){
        BTAFK.LOGGER.info("Loading BTAFK configuration");
        Toml toml = new Toml("BTAFK configuration. You can use <tags> whenever any text is specified. Available tags:\n\nControl/Conditional:\n\n<player> -> Returns formatted player name with colors and reset.\n<player:raw> -> Returns raw player display name, no colors.\n<server> -> Returns server_name property, with any formatting inside.\n\n For the following, anything in ( ) are equivalent and do the same thing.\n\nColor codes:\n\n<white>,<orange>,<magenta>,(<light blue>,<aqua>),<yellow>,(<lime>,<lime green>),<pink>,(<gray>,<grey>),(<light gray>,<light grey>,<silver>),(<cyan>,<turquoise>),<purple>,<blue>,<brown>,<green>,<red>,<black>\n\nFormatting Codes:\n\n(<obfuscated>,<ob>),(<bold>,<b>),(<strikethrough>,<st>),(<underline>,<u>),(<italic>,<i>),(<reset>,<r>)");
        
        toml.addEntry("server_name","Use this to specify what should be displayed with any <server> tags.\nDefault:This is a sample server name! If you are the server administrator, you can change this in [server folder]/config/btafk.cfg","This is a sample server name! If you are the server administrator, you can change this in [server folder]/config/btafk.cfg");
        
        toml.addCategory("AFK settings","afk");
        toml.addEntry("afk.global_enabled","Enables/Disables all AFK-Related functionality. \nDefault:true",true);
        toml.addEntry("afk.ticks_until_marked_afk","How many ticks idle until the server flags a player as afk. Minecraft runs at 20 ticks per second.\nDefault:6000 (5 Minutes)", 6000);
        toml.addEntry("afk.become_afk_message","The message sent to all players when a player becomes afk.\nDefault:<player> is now AFK.","<player> is now AFK.");
        toml.addEntry("afk.no_longer_afk_message", "The message sent to all players when a player is no longer afk.\nDefault:<player> is no longer AFK.", "<player> is no longer AFK.");
        toml.addEntry("afk.send_discord_messages","Enables/Disables sending discord messages via MelonUtilities when a player becomes/is no longer afk. Does nothing if MelonUtilities is not installed.\nDefault:true",true);
        
        toml.addCategory("Welcome Message settings.","welcome");
        toml.addEntry("welcome.global_enabled","Enables/Disables all Welcome Message-Related functionality.\nDefault:true",true);
        toml.addEntry("welcome.welcome_message", "The message sent to all players when a player is new to the server. \nDefault:<yellow><player:raw><aqua> is new to the server!","<yellow><player:raw><aqua> is new to the server!");
        toml.addEntry("welcome.tick_delay","How many ticks the server should wait before sending the Welcome message. Increase this if new players are having issues seeing Welcome messages, or if you want to specify what order messages should be displayed in. Anything below 1 is ignored.\nDefault:1",1);
        toml.addEntry("welcome.send_discord_messages","Enables/Disables sending discord messages via MelonUtilities when a player is new to the server. Does nothing if MelonUtilities is not installed.\nDefault:true",true);
        
        toml.addCategory("Message Of The Day settings","motd");
        toml.addEntry("motd.global_enabled","Enables/Disables all MOTD Message-Related functionality.\nDefault: false",false);
        toml.addEntry("motd.message_of_the_day","The message sent to a player when they join the server.\nDefault:<gray>This is a sample MOTD! If you, <player:raw>, are a server administrator, you can change this in [server_folder]/config/btafk.cfg!","<gray>This is a sample MOTD! If you, <player:raw>, are a server administrator, you can change this in [server_folder]/config/btafk.cfg!");
        toml.addEntry("motd.tick_delay","How many ticks the server should wait before sending the MOTD. Increase this if players are having issues seeing the MOTD, or if you want to specify what order messages should be displayed in. Anything below 1 is ignored.\nDefault:4",4);
        
        toml.addCategory("Welcome Back settings","returning");
        toml.addEntry("returning.global_enabled","Enables/Disables all Welcome Back-Related functionality.\nDefault: true",true);
        toml.addEntry("returning.returning_message","The message sent to a player who's joined back to the server after joining once. Does not display to new players, whereas the MOTD always will.\nDefault:Welcome back to <server>, <player>!","Welcome back to <server>, <player>!");
        toml.addEntry("returning.tick_delay","How many ticks the server should wait before sending the Welcome Back message. Increase this if players are having issues seeing the Welcome Back message, or if you want to specify what order messages should be displayed in. Anything below 1 is ignored.\nDefault:2",2);
        
        
        toml.addCategory("This is a test category","test");
        String[] stringArray = {"test","test2","test3"};
        
        toml.addEntry("test.testing",stringArray);
        
        // Load toml so we can access it.
        tomlConfigHandler = new TomlConfigHandler(BTAFK.MOD_ID, toml);
        
        
        
        serverName = loadWithFormatting("server_name");
        
        // AFK
        
        afkGlobalEnabled = tomlConfigHandler.getBoolean("afk.global_enabled");
        ticksUntilMarkedAfk = tomlConfigHandler.getInt("afk.ticks_until_marked_afk");
        becomeAfkMessage = loadWithFormatting("afk.become_afk_message");
        noLongerAfkMessage = loadWithFormatting("afk.no_longer_afk_message");
        afkSendDiscordMessages = tomlConfigHandler.getBoolean("afk.send_discord_messages");
        
        
        // Welcome
        welcomeGlobalEnabled = tomlConfigHandler.getBoolean("welcome.global_enabled");
        welcomeMessage = loadWithFormatting("welcome.welcome_message");
        welcomeTickDelay = tomlConfigHandler.getInt("welcome.tick_delay");
        welcomeSendDiscordMessages = tomlConfigHandler.getBoolean("welcome.send_discord_messages");
        
        
        // MOTD
        motdGlobalEnabled = tomlConfigHandler.getBoolean("motd.global_enabled");
        motdMessage = loadWithFormatting("motd.message_of_the_day");
        motdTickDelay = tomlConfigHandler.getInt("motd.tick_delay");
        
        
        // Returning
        returningGlobalEnabled = tomlConfigHandler.getBoolean("returning.global_enabled");
        returningMessage = loadWithFormatting("returning.returning_message");
        returningTickDelay = tomlConfigHandler.getInt("returning.tick_delay");
        
        
        
        if(BTAFK.DEBUG_MODE){
            ticksUntilMarkedAfk = 100;
            String[] testString = toml.get("test.test",String[].class);
            for(int i = 0; i < testString.length; i++){
                BTAFK.logInfoIfDebugMode(testString[i]);
            }
            
        }
        
        if(afkSendDiscordMessages || welcomeSendDiscordMessages){
            if(!MelonUtilitiesAPI.isMelonUtilitiesLoaded()){
                BTAFK.LOGGER.warn("MelonUtilities is not installed, but BTAFK has been configured to send discord messages, and will not do anything. If you want to send discord messages, install MelonUtilities.");
            }
        }
        
        BTAFK.LOGGER.info("Loaded btafk.cfg");
        
    }
    
    
    public static String loadWithFormatting(String key){
        return parseColorFormatTextForWeirdSystemsLikeRoseSMPsServerHost(tomlConfigHandler.getString(key));
    }
    
    public static String parseColorFormatTextForWeirdSystemsLikeRoseSMPsServerHost(String input){
        for(int i = 0; i < TextFormatting.FORMATTINGS.length; i++){
            TextFormatting currentFormat = TextFormatting.FORMATTINGS[i];
            String currentFormatToString = currentFormat.toString();
            String[] currentFormatNames = currentFormat.getNames();
            // For every name this current format has
            for(int j = 0; j < currentFormatNames.length; j++){
                // Syntax should be something like <red> for colors
                String simpleFormat = "<" + currentFormatNames[j].toLowerCase() + ">";
                input = input.replaceAll(simpleFormat,currentFormatToString);
            }
            // String formatCode = "<format=" + TextFormatting.FORMAT_CHARS.charAt(i) + ">";
            // String formatShorthand = "<f" + TextFormatting.FORMAT_CHARS.charAt(i) + ">";
            
            // input = input.replaceAll(formatCode, currentFormatToString);
            // input = input.replaceAll(formatShorthand, currentFormatToString);
        }
        
        input = input.replaceAll("<r>",TextFormatting.RESET.toString());
        input = input.replaceAll("<ob>",TextFormatting.OBFUSCATED.toString());
        input = input.replaceAll("<st>",TextFormatting.STRIKETHROUGH.toString());
        input = input.replaceAll("<b>", TextFormatting.BOLD.toString());
        input = input.replaceAll("<i>", TextFormatting.ITALIC.toString());
        input = input.replaceAll("<u>", TextFormatting.UNDERLINE.toString());
        
        return input;
    }
    

    
    public static void replaceAllLogger(String input, String format, String replacedBy){
        
        
        BTAFK.logInfoIfDebugMode("__________");
        BTAFK.logInfoIfDebugMode(input);
        BTAFK.logInfoIfDebugMode(format);
        BTAFK.logInfoIfDebugMode(replacedBy);
        
        
        input.replaceAll(format,replacedBy);
        
        
        BTAFK.logInfoIfDebugMode("output:");
        BTAFK.logInfoIfDebugMode(input);
        BTAFK.logInfoIfDebugMode("_________");
        
        
        
    }
    
    
}
