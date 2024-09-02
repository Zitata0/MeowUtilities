package com.zitata.meowutilities;

import com.zitata.meowutilities.commands.CommandManager;
import com.zitata.meowutilities.data.Config;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.events.PlayerConnectEvent;
import com.zitata.meowutilities.events.PlayerDisconnectEvent;
import com.zitata.meowutilities.events.ServerTickEvent;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.teleport.Teleporter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Map;

@Mod(
        modid = MeowUtilities.modId,
        name = MeowUtilities.modName,
        acceptableRemoteVersions = "*",
        version = MeowUtilities.version
)
public class MeowUtilities {
    public static final String modId = "meowutilities";
    public static final String modName = "MeowUtilities";
    public static final String version = "1.3.8";

    public static Data data = new Data();
    public static Config config = Data.readConfig();
    public static Teleporter teleporter = new Teleporter();
    /**
     * Key: playerTarget, Value: playerSource
     */
    public static Map<EntityPlayerMP, EntityPlayerMP> tpaRequestList = new HashMap<>();
    /**
     * Key: playerSource, Value: teleportDelay
     */
    public static Map<EntityPlayerMP, TeleportDelay> teleportDelayList = new HashMap<>();
    /**
     * Key: playerName, Value: playerGhost
     */
    public static Map<String, PlayerGhost> playerList = new HashMap<>();

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CommandManager.registryCommands(event);

        //Registration events
        FMLCommonHandler.instance().bus().register(new ServerTickEvent());
        FMLCommonHandler.instance().bus().register(new PlayerConnectEvent());
        FMLCommonHandler.instance().bus().register(new PlayerDisconnectEvent());
    }
}