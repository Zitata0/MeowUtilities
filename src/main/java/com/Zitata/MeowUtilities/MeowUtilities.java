package com.Zitata.MeowUtilities;

import com.Zitata.MeowUtilities.Commands.*;
import com.Zitata.MeowUtilities.Data.*;
import com.Zitata.MeowUtilities.Events.*;
import com.Zitata.MeowUtilities.Teleport.*;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import com.Zitata.MeowUtilities.Teleport.TpaRequest;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public static final String version = "1.3.0";

    public static Data data;
    public static Config config;

    public static Map<String, List<TeleportPoint>> teleportPoints;
    public static List<TpaRequest> tpaRequests;
    public static List<TeleportDelay> teleportDelays;

    public static Teleport teleport;

    public static final EnumChatFormatting SUCCESSFUL = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting ERROR = EnumChatFormatting.RED;
    public static final EnumChatFormatting PASSIVE = EnumChatFormatting.GRAY;

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        //Declaring static classes
        data = new Data();
        config = new Config();

        teleportPoints = new HashMap<String, List<TeleportPoint>>();
        tpaRequests = new ArrayList<TpaRequest>();
        teleportDelays = new ArrayList<TeleportDelay>();

        teleport = new Teleport();



        //Registration commands
        event.registerServerCommand(new SetTp());
        event.registerServerCommand(new Tp());
        event.registerServerCommand(new DelTp());
        event.registerServerCommand(new TpList());

        event.registerServerCommand(new Tpaccept());
        event.registerServerCommand(new Tpa());

        event.registerServerCommand(new ReloadCfg());
        event.registerServerCommand(new Suicide());

        //Registration events
        FMLCommonHandler.instance().bus().register(new onServerTick());
        FMLCommonHandler.instance().bus().register(new onPlayerConnect());
        FMLCommonHandler.instance().bus().register(new onPlayerDisconnect());
    }

    public void serverStopping(FMLServerStoppingEvent event){

        //Save data
        config.saveConfig();
    }
}