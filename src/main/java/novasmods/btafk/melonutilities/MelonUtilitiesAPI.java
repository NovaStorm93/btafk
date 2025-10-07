package novasmods.btafk.melonutilities;

import MelonUtilities.utility.discord.DiscordChatRelay;
import MelonUtilities.utility.discord.DiscordClient;
import club.minnced.discord.webhook.external.JDAWebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.fabricmc.loader.api.FabricLoader;
import novasmods.btafk.BTAFKConfigs;

public class MelonUtilitiesAPI {
    
    private static MelonUtilitiesAPI instance; 
    
    private JDAWebhookClient webhookClient;
    
    public static boolean isMelonUtilitiesLoaded(){
        return FabricLoader.getInstance().isModLoaded("melonutilities");
    }
    
    public static MelonUtilitiesAPI getInstance(){
        if(instance == null){
            instance = new MelonUtilitiesAPI();
        }
        
        return instance;
        
        
        
    }
    
    
    private MelonUtilitiesAPI(){
        
        
        
        if(isMelonUtilitiesLoaded()){
            webhookClient = DiscordClient.getWebhook();
        }
        
        
    }
    
    public void discordSendPlayerNowAfk(String username){
        if(!isMelonUtilitiesLoaded() || !BTAFKConfigs.afkSendDiscordMessages || !BTAFKConfigs.afkGlobalEnabled){
            return;
        }
        
        String avatarUrl = "https://www.mc-heads.net/head/" + username;
        
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x5c5c5c)
                .setAuthor(new WebhookEmbed.EmbedAuthor(username + " is now afk.", avatarUrl, null))
                .build();
        DiscordChatRelay.sendMessage(null, embed);
    }
    public void discordSendPlayerNotAfk(String username){
        if(!isMelonUtilitiesLoaded() || !BTAFKConfigs.afkSendDiscordMessages || !BTAFKConfigs.afkGlobalEnabled){
            return;
        }
        
        String avatarUrl = "https://www.mc-heads.net/head/" + username;
        
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xebeb0e)
                .setAuthor(new WebhookEmbed.EmbedAuthor(username + " is no longer afk.", avatarUrl, null))
                .build();
        DiscordChatRelay.sendMessage(null, embed);
        
        
        
    }
    public void discordSendPlayerFirstJoin(String username){
        if(!isMelonUtilitiesLoaded() || !BTAFKConfigs.welcomeSendDiscordMessages || !BTAFKConfigs.welcomeGlobalEnabled){
            return;
        }
        
        String avatarUrl = "https://www.mc-heads.net/head/" + username;
        
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x0cbaeb)
                .setAuthor(new WebhookEmbed.EmbedAuthor(username + " is new to the server!.", avatarUrl, null))
                .build();
        DiscordChatRelay.sendMessage(null, embed);
    }
    
    
}
