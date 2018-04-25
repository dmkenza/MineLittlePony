package com.minelittlepony;

import com.minelittlepony.hdskins.gui.GuiSkinsMineLP;
import com.minelittlepony.pony.data.IPonyData;
import com.minelittlepony.pony.data.PonyDataSerialzier;
import com.mumfrey.liteloader.core.LiteLoader;
import com.voxelmodpack.hdskins.HDSkinManager;
import com.voxelmodpack.hdskins.gui.GuiSkins;
import com.voxelmodpack.hdskins.skins.SkinServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.settings.KeyBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

/**
 * Static MineLittlePony singleton class. Everything's controlled from up here.
 */
public class MineLittlePony {

    public static final Logger logger = LogManager.getLogger("MineLittlePony");

    public static final String
        MOD_NAME = "Mine Little Pony",
        MOD_VERSION = "@VERSION@";

	private static final String
	    SKIN_SERVER_URL = "minelpskins.voxelmodpack.com",
	    GATEWAY_URL = "minelpskinmanager.voxelmodpack.com";

    private static final KeyBinding SETTINGS_GUI = new KeyBinding("Settings", Keyboard.KEY_F9, "Mine Little Pony");

    private static MineLittlePony instance;

    private final PonyConfig config;
    private final PonyManager ponyManager;

    private final PonyRenderManager renderManager;

    MineLittlePony() {
        instance = this;

        LiteLoader.getInput().registerKeyBinding(SETTINGS_GUI);

        config = new PonyConfig();
        ponyManager = new PonyManager(config);
        renderManager = new PonyRenderManager();
                
        LiteLoader.getInstance().registerExposable(config, null);

        IReloadableResourceManager irrm = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
        irrm.registerReloadListener(ponyManager);

        MetadataSerializer ms = Minecraft.getMinecraft().getResourcePackRepository().rprMetadataSerializer;
        ms.registerMetadataSectionType(new PonyDataSerialzier(), IPonyData.class);

        // This also makes it the default gateway server.
        SkinServer.defaultServers.add("legacy:" + SKIN_SERVER_URL + ";" + GATEWAY_URL);
    }

    /**
     * Called when the game is ready.
     */
    void postInit(Minecraft minecraft) {

        HDSkinManager manager = HDSkinManager.INSTANCE;
//        manager.setSkinUrl(SKIN_SERVER_URL);
//        manager.setGatewayURL(GATEWAY_URL);
        manager.addSkinModifier(new PonySkinModifier());
//        logger.info("Set MineLP skin server URL.");

        RenderManager rm = minecraft.getRenderManager();
        renderManager.initialisePlayerRenderers(rm);
        renderManager.initializeMobRenderers(rm, config);
    }

    /**
     * Called on every update tick
     */
    void onTick(Minecraft minecraft, boolean inGame) {

        if (inGame && minecraft.currentScreen == null && SETTINGS_GUI.isPressed()) {
            minecraft.displayGuiScreen(new PonySettingPanel());
        }

        boolean skins = minecraft.currentScreen instanceof GuiSkins
                && !(minecraft.currentScreen instanceof GuiSkinsMineLP);
        if (skins) {
            minecraft.displayGuiScreen(new GuiSkinsMineLP(ponyManager));
        }
        HDSkinManager.INSTANCE.setEnabled(config.hd);

    }

    /**
     * Gets the global MineLP instance.
     */
    public static MineLittlePony getInstance() {
        return instance;
    }

    /**
     * Gets the static pony manager instance.
     */
    public PonyManager getManager() {
        return ponyManager;
    }
    
    /**
     * Gets the static pony render manager responsible for all entity renderers.
     */
    public PonyRenderManager getRenderManager() {
        return renderManager;
    }

    /**
     * Gets the global MineLP client configuration.
     */
    public static PonyConfig getConfig() {
        return getInstance().config;
    }

}
