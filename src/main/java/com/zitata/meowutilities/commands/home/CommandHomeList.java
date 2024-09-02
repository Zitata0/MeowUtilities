package com.zitata.meowutilities.commands.home;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class CommandHomeList extends CommandBase {

    @Override
    public String getCommandName() {
        return "homelist";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.homelist.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("homes");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.teleportPoints.isEmpty()) {
            throw new CommandException("commands.teleportpoint.player.nopoints");
        }

        MessageSender.sendTranslatedMessage(player, MessageSender.SUCCESSFUL, "commands.homelist.header", playerGhost.teleportPoints.size(), MeowUtilities.config.getTpCount());
        MessageSender.sendTranslatedMessage(player, MessageSender.SUCCESSFUL, "commands.homelist.private", playerGhost.getPrivateTeleportPoints().keySet().toString());
        MessageSender.sendTranslatedMessage(player, MessageSender.SUCCESSFUL, "commands.homelist.public", playerGhost.getPublicTeleportPoints().keySet().toString());
    }
}
