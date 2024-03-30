package com.Zitata.MeowUtilities;

import com.Zitata.MeowUtilities.Commands.*;
import com.Zitata.MeowUtilities.Data.*;
import com.Zitata.MeowUtilities.Events.*;
import com.Zitata.MeowUtilities.Teleport.*;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

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
    public static List<Cooldown> cooldowns;

    public static Teleport teleport;

    public static final EnumChatFormatting SUCCESSFUL = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting ERROR = EnumChatFormatting.RED;
    public static final EnumChatFormatting PASSIVE = EnumChatFormatting.GRAY;

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        //Declaring static classes
        data = new Data();
        config = new Config();
        teleport = new Teleport();

        teleportPoints = new HashMap<String, List<TeleportPoint>>();
        tpaRequests = new ArrayList<TpaRequest>();
        teleportDelays = new ArrayList<TeleportDelay>();
        System.out.println("Cooldowns created----------------------------------------------Meow123");
        cooldowns = new ArrayList<Cooldown>();
        MeowUtilities.cooldowns.add(new Cooldown("MeowPurr"));

        //Read files
        data.readTeleportPoints();
        data.readCooldown();

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

        /*

        //1
        class Meow{
            private String name;
            private Integer value;
            public Meow(String name, Integer value){
                this.name = name;
                this.value = value;
            }
            public String getName() {
                return this.name;
            }
            public Integer getValue() {
                return this.value;
            }
        }

        List<Meow> meowList = new ArrayList<Meow>();

        Integer resultMeow;
        for (Meow meow : meowList){
            if (meow.equals("2")){
                resultMeow = meow.value;
                break;
            }
        }

        //2
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("1", 1);
        map.put("2", 2);

        meowList.add(new Meow("1", 1));
        meowList.add(new Meow("2", 2));

        Integer resultMap;
        resultMap = map.get("2");

        */
    }
}