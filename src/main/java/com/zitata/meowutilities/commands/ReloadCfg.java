package com.zitata.meowutilities.commands;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class ReloadCfg extends CommandBase {
    @Override
    public String getCommandName() {
        return "reloadcfg";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/reloadcfg";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        MeowUtilities.config = Data.readConfig();

        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            MessageSender.sendMessage(player, MeowUtilities.SUCCESSFUL, "Configuration is reloaded");
        } else {
            System.out.println("Configuration is reloaded");
        }
    }
}
