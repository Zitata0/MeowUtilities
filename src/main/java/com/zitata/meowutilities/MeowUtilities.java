package com.zitata.meowutilities;

import com.zitata.meowutilities.commands.CommandManager;
import com.zitata.meowutilities.data.Config;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.events.PlayerConnectEvent;
import com.zitata.meowutilities.events.PlayerDisconnectEvent;
import com.zitata.meowutilities.events.ServerTickEvent;
import com.zitata.meowutilities.teleport.TeleportDelay;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Map;

@Mod(
        modid = MeowUtilities.MOD_ID,
        name = MeowUtilities.MOD_NAME,
        acceptableRemoteVersions = "*",
        version = MeowUtilities.VERSION
)
public class MeowUtilities {
    public static final String MOD_ID = "meowutilities";
    public static final String MOD_NAME = "MeowUtilities";
    public static final String VERSION = "1.3.9";

    @Mod.Instance(MOD_ID)
    public static MeowUtilities INSTANCE;

    public Config config;

    /**
     * Key: playerTarget, Value: playerSource
     */
    public static final Map<EntityPlayerMP, EntityPlayerMP> tpaRequestList = new HashMap<>();
    /**
     * Key: playerSource, Value: teleportDelay
     */
    public static final Map<EntityPlayerMP, TeleportDelay> teleportDelayList = new HashMap<>();
    /**
     * Key: playerName, Value: playerGhost
     */
    public static final Map<String, PlayerGhost> playerList = new HashMap<>();

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        Data.createDirs();
        config = Data.readConfig();
        CommandManager.registryCommands(event);

        //Registration events
        FMLCommonHandler.instance().bus().register(new ServerTickEvent());
        FMLCommonHandler.instance().bus().register(new PlayerConnectEvent());
        FMLCommonHandler.instance().bus().register(new PlayerDisconnectEvent());
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        Data.saveAllPlayerGhost(playerList);
    }
}