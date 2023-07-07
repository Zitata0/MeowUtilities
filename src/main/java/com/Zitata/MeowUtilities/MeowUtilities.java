package com.Zitata.MeowUtilities;

import com.Zitata.MeowUtilities.Commands.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = MeowUtilities.MODID, acceptableRemoteVersions = "*", version = "1.1")
public class MeowUtilities
{
    public static final String MODID = "MeowU";
    public static DataStorage dataStorage;
    public static Teleport teleport;
    public static List<TpaRequest> tpaRequests;
    public static List<TeleportDelay> teleportDelays;
    public static Config config;

    public static final EnumChatFormatting SUCCESSFUL = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting ERROR = EnumChatFormatting.RED;
    public static final EnumChatFormatting PASSIVE = EnumChatFormatting.GRAY;


    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        //Объявление статичных классов
        dataStorage = new DataStorage();
        teleport = new Teleport();
        tpaRequests = new ArrayList<TpaRequest>(); //Список запросов на телепортацию
        teleportDelays = new ArrayList<TeleportDelay>(); //Список задержек телепортации
        config = dataStorage.getConfig();

        //Регистрация команд
        event.registerServerCommand(new CommandSetHome());
        event.registerServerCommand(new CommandHome());
        event.registerServerCommand(new CommandDelHome());
        event.registerServerCommand(new CommandTpa());
        event.registerServerCommand(new CommandTpaccept());
        event.registerServerCommand(new CommandReloadCfg());
        event.registerServerCommand(new CommandSuicide());
        event.registerServerCommand(new HomeList());

        //Регистрация событий
        FMLCommonHandler.instance().bus().register(new FMLEventListener());
    }
}
