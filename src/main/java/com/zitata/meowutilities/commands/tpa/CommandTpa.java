package com.zitata.meowutilities.commands.tpa;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class CommandTpa extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpa";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.tpa.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length > 1) {
            return null;
        }
        return getListOfStringsMatchingLastWord(args, ((EntityPlayerMP)sender).mcServer.getAllUsernames());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 1) {
            throw new CommandException("commands.tpa.usage");
        }

        EntityPlayerMP playerSource = (EntityPlayerMP) sender;
        PlayerGhost playerSourceGhost = MeowUtilities.playerList.get(playerSource.getDisplayName());

        if (!playerSourceGhost.getCooldown().isTpa()) {
            MessageSender.sendTranslatedMessage(playerSource, MessageSender.ERROR, "commands.recharge.tpa", ((playerSourceGhost.getCooldown().getTpa() - System.currentTimeMillis()) / 1000));
            return;
        }

        EntityPlayerMP playerTarget = null;

        for (EntityPlayerMP player : playerSource.mcServer.getConfigurationManager().playerEntityList) {
            if (player.getDisplayName().equals(args[0])) {
                playerTarget = player;
                break;
            }
        }

        if (playerTarget == null) {
            throw new PlayerNotFoundException();
        }

        MeowUtilities.tpaRequestList.put(playerTarget, playerSource);
        MessageSender.sendTranslatedMessage(playerSource, MessageSender.PASSIVE, "commands.tpa.request.send", playerTarget.getDisplayName());
        MessageSender.sendTranslatedMessage(playerTarget, MessageSender.PASSIVE, "commands.tpa.request.receive", playerSource.getDisplayName());
        MessageSender.sendTranslatedMessage(playerTarget, MessageSender.SUCCESSFUL, "commands.tpa.request.accept");
    }
}
