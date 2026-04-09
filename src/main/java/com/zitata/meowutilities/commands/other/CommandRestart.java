package com.zitata.meowutilities.commands.other;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.util.MessageSender;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandRestart extends CommandBase {
    @Override
    public String getCommandName() {
        return "restart";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/restart";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        FMLCommonHandler.instance().getMinecraftServerInstance().initiateShutdown();
    }
}
