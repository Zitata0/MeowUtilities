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
        return "commands.sethome.usage";
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
        return getListOfStringsFromIterableMatchingLastWord(args, MeowUtilities.playerList.get(((EntityPlayerMP)sender).getDisplayName()).teleportPoints.keySet());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;

        if (!((player.dimension == 0) || (player.dimension >= 400 && player.dimension <= 500))) {
            throw new CommandException("commands.teleportpoint.blacklist.add");
        }

        String teleportPointName;
        if (args.length > 0) {
            teleportPointName = args[0];
        } else {
            teleportPointName = "home";
        }

        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.teleportPoints.size() > MeowUtilities.config.getTpCount()) {
            throw new CommandException("commands.teleportpoint.player.limit");
        }

        if (playerGhost.teleportPoints.containsKey(teleportPointName)) {
            TeleportDelay.removeDelays(MeowUtilities.teleportDelayList, playerGhost.teleportPoints.get(teleportPointName));

            playerGhost.teleportPoints.get(teleportPointName).setPoint(player.dimension, player.posX, player.posY, player.posZ, player.rotationYawHead, player.rotationPitch);
            playerGhost.teleportPoints.get(teleportPointName).setPublic(false);
            MessageSender.sendTranslatedMessage(player, MessageSender.PASSIVE, "commands.teleportpoint.added", teleportPointName);
        } else if (playerGhost.teleportPoints.size() >= MeowUtilities.config.getTpCount()) {
            throw new CommandException("commands.teleportpoint.player.limit");
        } else {
            playerGhost.teleportPoints.put(teleportPointName, new TeleportPoint(teleportPointName, player.dimension, player.posX, player.posY, player.posZ, player.rotationYawHead, player.rotationPitch));
            MessageSender.sendTranslatedMessage(player, MessageSender.SUCCESSFUL, "commands.teleportpoint.added", teleportPointName);
        }
    }
}
