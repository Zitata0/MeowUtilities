package com.zitata.meowutilities.commands.home;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.teleport.TeleportPoint;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class CommandSetHome extends CommandBase {

    @Override
    public String getCommandName() {
        return "sethome";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/sethome [null/title]";
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
        return getListOfStringsFromIterableMatchingLastWord(args, MeowUtilities.playerList.get(((EntityPlayerMP)sender).getGameProfile().getId().toString()).teleportPoints.keySet());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;

        /*
        String dimName = player.mcServer.worldServerForDimension(player.dimension).provider.getDimensionName();
        if (!(player.dimension == 0 || dimName.contains("PERSONAL") || dimName.contains("SPACESTATION"))) {
            throw new CommandException("You do not have permission to create a home in this world");
        }
        */

        String teleportPointName;
        if (args.length > 0) {
            teleportPointName = args[0];
        } else {
            teleportPointName = "home";
        }

        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getGameProfile().getId().toString());

        if (playerGhost.teleportPoints.size() > MeowUtilities.INSTANCE.config.getTpCount()) {
            throw new CommandException("You have a lot of teleport points");
        }

        if (playerGhost.teleportPoints.containsKey(teleportPointName)) {
            TeleportDelay.removeDelays(MeowUtilities.teleportDelayList, playerGhost.teleportPoints.get(teleportPointName));

            playerGhost.teleportPoints.get(teleportPointName).setPoint(player.dimension, player.posX, player.posY, player.posZ, player.rotationYawHead, player.rotationPitch);
            playerGhost.teleportPoints.get(teleportPointName).setPublic(false);
            MessageSender.sendMessage(player, MessageSender.PASSIVE, String.format("The '%s' has been added", teleportPointName));
        } else if (playerGhost.teleportPoints.size() >= MeowUtilities.INSTANCE.config.getTpCount()) {
            throw new CommandException("You have a lot of teleport points");
        } else {
            playerGhost.teleportPoints.put(teleportPointName, new TeleportPoint(teleportPointName, player.dimension, player.posX, player.posY, player.posZ, player.rotationYawHead, player.rotationPitch));
            MessageSender.sendMessage(player, MessageSender.SUCCESSFUL, String.format("The '%s' has been added", teleportPointName));
        }
    }
}
