package com.zitata.meowutilities.commands;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class Tpa extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpa";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/tpa [name]";
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
        return Arrays.asList(((EntityPlayerMP)sender).mcServer.getAllUsernames());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP playerSource = (EntityPlayerMP) sender;
        PlayerGhost playerSourceGhost = MeowUtilities.playerList.get(playerSource.getDisplayName());

        if (!playerSourceGhost.getCooldown().isTpa()) {
            MessageSender.sendMessage(playerSource, MeowUtilities.ERROR, "Tpa will recharge in " + ((playerSourceGhost.getCooldown().getTpa() - System.currentTimeMillis()) / 1000) + " seconds");
            return;
        }

        if (args.length < 1) {
            MessageSender.sendMessage(playerSource, MeowUtilities.ERROR, "Player not found");
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
            MessageSender.sendMessage(playerSource, MeowUtilities.ERROR, "Player not found");
            return;
        }

        MeowUtilities.tpaRequestList.put(playerTarget, playerSource);
        MessageSender.sendMessage(playerSource, MeowUtilities.PASSIVE, "Request has been sent to " + playerTarget.getDisplayName());
        MessageSender.sendMessage(playerTarget, MeowUtilities.PASSIVE, playerSource.getDisplayName() + " wants to teleport to you");
        MessageSender.sendMessage(playerTarget, MeowUtilities.SUCCESSFUL, "Use \"" + "/tpaccept" + "\" to accept teleport");
    }
}
