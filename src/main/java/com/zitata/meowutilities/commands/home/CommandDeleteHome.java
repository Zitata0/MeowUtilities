package com.zitata.meowutilities.commands.home;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class CommandDeleteHome extends CommandBase {
    @Override
    public String getCommandName() {
        return "delhome";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return '/' + getCommandName() + " [name]";
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
        return Arrays.asList(MeowUtilities.playerList.get(((EntityPlayerMP)sender).getDisplayName()).teleportPoints.keySet().toArray());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.teleportPoints.isEmpty()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "You do not have teleport points");
            return;
        }

        String teleportPointName;
        if (args.length < 1) {
            MessageSender.sendMessage(player, MessageSender.NEUTRAL, getCommandUsage(sender));
            return;
        } else {
            teleportPointName = args[0];
        }

        if (!playerGhost.teleportPoints.containsKey(teleportPointName)) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Teleport point not found");
            return;
        }

        TeleportDelay.removeDelays(MeowUtilities.teleportDelayList, playerGhost.teleportPoints.get(teleportPointName));

        playerGhost.teleportPoints.remove(teleportPointName);
        MessageSender.sendMessage(player, MessageSender.SUCCESSFUL, "The \"" + teleportPointName + "\" has been removed");
    }
}
