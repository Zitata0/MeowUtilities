package com.zitata.meowutilities;

import com.zitata.meowutilities.commands.*;
import com.zitata.meowutilities.data.Config;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.events.PlayerConnectEvent;
import com.zitata.meowutilities.events.PlayerDisconnectEvent;
import com.zitata.meowutilities.events.ServerTickEvent;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.Teleporter;
import com.zitata.meowutilities.teleport.TeleportDelay;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;
import java.util.Map;

@Mod(
        modid = MeowUtilities.modId,
        name = MeowUtilities.modName,
        acceptableRemoteVersions = "*",
        version = MeowUtilities.version
)
public class MeowUtilities {
    public static final String modId = "MeowU";
    public static final String modName = "MeowUtilities";
    public static final String version = "1.3.5";

    public static Data data = new Data();
    public static Config config = Data.readConfig();
    public static Teleporter teleporter = new Teleporter();
    /**
     * Key: playerTarget, Value: playerSource
     */
    public static Map<EntityPlayerMP, EntityPlayerMP> tpaRequestList = new HashMap<EntityPlayerMP, EntityPlayerMP>();
    /**
     * Key: playerSource, Value: teleportDelay
     */
    public static Map<EntityPlayerMP, TeleportDelay> teleportDelayList = new HashMap<EntityPlayerMP, TeleportDelay>();
    /**
     * Key: playerName, Value: playerGhost
     */
    public static Map<String, PlayerGhost> playerList = new HashMap<String, PlayerGhost>();

    public static final EnumChatFormatting SUCCESSFUL = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting ERROR = EnumChatFormatting.RED;
    public static final EnumChatFormatting PASSIVE = EnumChatFormatting.GRAY;
    public static final EnumChatFormatting NEUTRAL = EnumChatFormatting.WHITE;

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        //Registration commands
        event.registerServerCommand(new SetTp());
        event.registerServerCommand(new Tp());
        event.registerServerCommand(new DelTp());
        event.registerServerCommand(new TpList());
        event.registerServerCommand(new SetPublic());

        event.registerServerCommand(new Tpaccept());
        event.registerServerCommand(new Tpa());

        event.registerServerCommand(new ReloadCfg());
        event.registerServerCommand(new Suicide());

        //Registration events
        FMLCommonHandler.instance().bus().register(new ServerTickEvent());
        FMLCommonHandler.instance().bus().register(new PlayerConnectEvent());
        FMLCommonHandler.instance().bus().register(new PlayerDisconnectEvent());
    }
}