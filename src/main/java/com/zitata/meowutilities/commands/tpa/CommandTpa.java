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
        return "/tpa [playerName]";
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
            throw new CommandException(getCommandUsage(sender));
        }

        EntityPlayerMP playerSource = (EntityPlayerMP) sender;
        PlayerGhost playerSourceGhost = MeowUtilities.playerList.get(playerSource.getDisplayName());

        if (!playerSourceGhost.getCooldown().isTpa()) {
            MessageSender.sendMessage(playerSource, MessageSender.ERROR, String.format("Tpa will recharge in %s seconds", ((playerSourceGhost.getCooldown().getTpa() - System.currentTimeMillis()) / 1000)));
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
        MessageSender.sendMessage(playerSource, MessageSender.PASSIVE, String.format("Request has been sent to %s", playerTarget.getDisplayName()));
        MessageSender.sendMessage(playerTarget, MessageSender.PASSIVE, String.format("%s wants to teleport to you", playerSource.getDisplayName()));
        MessageSender.sendMessage(playerTarget, MessageSender.SUCCESSFUL, "Use '/tpaccept' to accept teleport");
    }
}
