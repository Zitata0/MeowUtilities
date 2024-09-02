package com.zitata.meowutilities.commands.home;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
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
        return "commands.home.usage";
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
            PlayerGhost playerGhostTarget;
            if (MeowUtilities.playerList.containsKey(args[0])) {
                playerGhostTarget = MeowUtilities.playerList.get(args[0]);
            } else if ((playerGhostTarget = Data.getPlayerGhost(args[0])) == null) {
                throw new PlayerNotFoundException();
            }
            targets.addAll(playerGhostTarget.getPublicTeleportPoints().keySet());
        }
        return getListOfStringsFromIterableMatchingLastWord(args, targets);
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
            throw new CommandException("commands.recharge.home.public", ((playerSourceGhost.getCooldown().getTpPublic() - System.currentTimeMillis()) / 1000));
        }

        if (MeowUtilities.playerList.containsKey(playerTargetName)) {
            playerTargetGhost = MeowUtilities.playerList.get(playerTargetName);
        } else if ((playerTargetGhost = Data.getPlayerGhost(playerTargetName)) == null) {
            throw new PlayerNotFoundException();
        }

        if (!playerTargetGhost.hasPublicTeleportPoint()) {
            throw new CommandException("commands.teleportpoint.public.player.other.nopoints", playerTargetName);
        }

        if (!playerTargetGhost.teleportPoints.containsKey(teleportPointName)) {
            throw new CommandException("commands.teleportpoint.notfound");
        }

        if (!playerTargetGhost.teleportPoints.get(teleportPointName).isPublic()) {
            throw new CommandException("commands.teleportpoint.public.notpublic");
        }

        MeowUtilities.teleportDelayList.put(player, new TeleportDelay(player, playerTargetGhost.teleportPoints.get(teleportPointName)));
    }

    private void privateTp(EntityPlayerMP player, String teleportPointName) {
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (!playerGhost.getCooldown().isTp()) {
            throw new CommandException("commands.recharge.home.private", ((playerGhost.getCooldown().getTp() - System.currentTimeMillis()) / 1000));
        }

        if (playerGhost.teleportPoints.isEmpty()) {
            throw new CommandException("commands.teleportpoint.player.nopoints");
        }

        if (!playerGhost.teleportPoints.containsKey(teleportPointName)) {
            throw new CommandException("commands.teleportpoint.notfound");
        }

        MeowUtilities.teleportDelayList.put(player, new TeleportDelay(player, playerGhost.teleportPoints.get(teleportPointName)));
    }
}
