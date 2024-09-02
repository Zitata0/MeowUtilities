package com.zitata.meowutilities.commands.home;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.TeleportPoint;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;

public class CommandSetPublicHome extends CommandBase {

    @Override
    public String getCommandName() {
        return "setpublic";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.setpublichome.usage";
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

        if (args.length < 1) {
            MessageSender.sendMessage(player, MessageSender.NEUTRAL, getCommandUsage(sender));
            return;
        }

        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.teleportPoints.isEmpty()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, "You do not have teleport points");
            return;
        }

        TeleportPoint teleportPoint;
        if (playerGhost.teleportPoints.containsKey(args[0])) {
            if (!playerGhost.teleportPoints.get(args[0]).isPublic() && playerGhost.publicTeleportPointCount() >= MeowUtilities.config.getTpPublicCount()) {
                MessageSender.sendMessage(player, MessageSender.ERROR, "You have a lot of public teleport points");
                return;
            }
            teleportPoint = playerGhost.teleportPoints.get(args[0]);
        } else {
            MessageSender.sendMessage(player, MessageSender.ERROR, "Teleport point not found");
            return;
        }

        teleportPoint.setPublic(!teleportPoint.isPublic());
        MessageSender.sendMessage(player, MessageSender.PASSIVE, "The \"" + teleportPoint.getName() + "\" has been modified");
    }
}
