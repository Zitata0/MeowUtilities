package com.zitata.meowutilities.commands;

import com.zitata.meowutilities.commands.home.*;
import com.zitata.meowutilities.commands.other.CommandReloadConfig;
import com.zitata.meowutilities.commands.other.CommandSpawn;
import com.zitata.meowutilities.commands.other.CommandSuicide;
import com.zitata.meowutilities.commands.tpa.CommandTpa;
import com.zitata.meowutilities.commands.tpa.CommandTpaccept;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandManager {
    public static void registryCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetHome());
        event.registerServerCommand(new CommandHome());
        event.registerServerCommand(new CommandDeleteHome());
        event.registerServerCommand(new CommandHomeList());
        event.registerServerCommand(new CommandSetPublicHome());

        event.registerServerCommand(new CommandTpaccept());
        event.registerServerCommand(new CommandTpa());

        event.registerServerCommand(new CommandReloadConfig());
        event.registerServerCommand(new CommandSuicide());

        event.registerServerCommand(new CommandSpawn());
    }
}
