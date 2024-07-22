package com.zitata.meowutilities.commands;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.util.MessageSender;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.TeleportPoint;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetTp extends CommandBase {

    @Override
    public String getCommandName() {
        return "stp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/stp [null/name]";
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
    public List<String> getCommandAliases() {
        List<String> list = new ArrayList<String>();
        list.add("sethome");
        list.add("settp");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;

        if (!((player.dimension == 0) || (player.dimension >= 400 && player.dimension <= 500))) {
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "You do not have permission to create a home in this world");
            return;
        }

        String teleportPointName;
        if (args.length > 0) {
            teleportPointName = args[0];
        } else {
            teleportPointName = "home";
        }

        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.teleportPoints.size() > MeowUtilities.config.getTpCount()) {
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "You have a lot of teleport points");
            return;
        }

        if (playerGhost.teleportPoints.containsKey(teleportPointName)) {
            TeleportDelay.removeDelays(MeowUtilities.teleportDelayList, playerGhost.teleportPoints.get(teleportPointName));

            playerGhost.teleportPoints.get(teleportPointName).setPoint(player.dimension, player.posX, player.posY, player.posZ, player.rotationYawHead, player.rotationPitch);
            playerGhost.teleportPoints.get(teleportPointName).setPublic(false);
            MessageSender.sendMessage(player, MeowUtilities.PASSIVE, "The \"" + teleportPointName + "\" has been modified");
        } else if (playerGhost.teleportPoints.size() >= MeowUtilities.config.getTpCount()) {
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "You have a lot of teleport points");
        } else {
            playerGhost.teleportPoints.put(teleportPointName, new TeleportPoint(teleportPointName, player.dimension, player.posX, player.posY, player.posZ, player.rotationYawHead, player.rotationPitch));
            MessageSender.sendMessage(player, MeowUtilities.SUCCESSFUL, "The \"" + teleportPointName + "\" has been added");
        }
    }
}
