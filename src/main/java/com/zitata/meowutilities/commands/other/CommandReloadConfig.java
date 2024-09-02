package com.zitata.meowutilities.commands.other;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandReloadConfig extends CommandBase {
    @Override
    public String getCommandName() {
        return "reloadcfg";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.reloadcfg.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        MeowUtilities.config = Data.readConfig();
        EntityPlayerMP player = (EntityPlayerMP) sender;
        MessageSender.sendMessage(player, MessageSender.SUCCESSFUL, "Configuration is reloaded");
    }
}
