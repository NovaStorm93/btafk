package novasmods.btafk;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class BTAFK implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
    public static final String MOD_ID = "btafk";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int TICKS_UNTIL_AFK = 6000;
	// Unused
	public static int SECONDS_UNTIL_AFK = 300;
	public static int MINS_UNTIL_AFK = 5;
	public static boolean DEBUG_MODE = false;
	// !Unused
	
	
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) serverInitialize();
		else clientInitialize();
		
    }

	public void clientInitialize(){
		LOGGER.warn("This mod is currently not intended to be run client-side and does nothing. Please remove this mod from your client");
	}
	
	
	public void serverInitialize(){
		if(DEBUG_MODE){
			TICKS_UNTIL_AFK = 100;
		}
	}
	
	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}
}
