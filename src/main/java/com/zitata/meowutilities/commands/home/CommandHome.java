package com.zitata.meowutilities.commands.home;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHome extends CommandBase {

    @Override
    public String getCommandName() {
        return "home";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return '/' + getCommandName() + " [null/name]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length > 2) {
            return null;
        }
        EntityPlayerMP player = ((EntityPlayerMP)sender);
        List<String> targets = new ArrayList<>();
        if (args.length == 1) {
            targets.addAll(MeowUtilities.playerList.get(player.getDisplayName()).teleportPoints.keySet());
            targets.addAll(Arrays.asList(player.mcServer.getAllUsernames()));
        } else {
            PlayerGhost playerGhostTarget = null;
            if (MeowUtilities.playerList.containsKey(args[0])) {
                playerGhostTarget = MeowUtilities.playerList.get(args[0]);
            } else {
                playerGhostTarget = Data.getPlayerGhost(args[0]);
            }
            targets.addAll(playerGhostTarget.getPublicTeleportPoints().keySet());
        }
        return targets;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (args.length > 1) {
            publicTp(player, args[0], args[1]);
        } else if (args.length > 0) {
            privateTp(player, args[0]);
        } else {
            privateTp(player, "home");
        }
    }

    private void publicTp(EntityPlayerMP player, String playerTargetName, String teleportPointName) {
        PlayerGhost playerTargetGhost;
        PlayerGhost playerSourceGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (!playerSourceGhost.getCooldown().isTpPublic()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Tp to public point will recharge in " + ((playerSourceGhost.getCooldown().getTpPublic() - System.currentTimeMillis()) / 1000) + " seconds");
            return;
        }

        if (MeowUtilities.playerList.containsKey(playerTargetName)) {
            playerTargetGhost = MeowUtilities.playerList.get(playerTargetName);
        } else {
            playerTargetGhost = Data.getPlayerGhost(playerTargetName);
            /**
             * MessageSender.sendMessage(player, MeowUtilities.ERROR, playerTargetName + " not found");
             * return;
             *
             */
        }

        if (!playerTargetGhost.hasPublicTeleportPoint()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, playerTargetName + " do not have public teleport points");
            return;
        }

        if (!playerTargetGhost.teleportPoints.containsKey(teleportPointName)) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Teleport point not found");
            return;
        }

        if (!playerTargetGhost.teleportPoints.get(teleportPointName).isPublic()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Teleport point is not public");
            return;
        }

        MeowUtilities.teleportDelayList.put(player, new TeleportDelay(player, playerTargetGhost.teleportPoints.get(teleportPointName)));
    }

    private void privateTp(EntityPlayerMP player, String teleportPointName) {
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (!playerGhost.getCooldown().isTp()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Tp to private point will recharge in " + ((playerGhost.getCooldown().getTp() - System.currentTimeMillis()) / 1000) + " seconds");
            return;
        }

        if (playerGhost.teleportPoints.isEmpty()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "You do not have teleport points");
            return;
        }

        if (!playerGhost.teleportPoints.containsKey(teleportPointName)) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Teleport point not found");
            return;
        }

        MeowUtilities.teleportDelayList.put(player, new TeleportDelay(player, playerGhost.teleportPoints.get(teleportPointName)));
    }
}
