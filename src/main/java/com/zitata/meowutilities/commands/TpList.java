package com.zitata.meowutilities.commands;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class TpList extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpl";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/tpl";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> list = new ArrayList<String>();
        list.add("homes");
        list.add("homelist");
        list.add("tplist");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.teleportPoints.isEmpty()) {
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "You do not have homes");
            return;
        }

        MessageSender.sendMessage(player, MeowUtilities.SUCCESSFUL, "TeleportPoints (" + playerGhost.teleportPoints.size() + "/" + MeowUtilities.config.getTpCount() + ")");
        MessageSender.sendMessage(player, MeowUtilities.SUCCESSFUL, "Private: " + playerGhost.getPrivateTeleportPoints().keySet().toString());
        MessageSender.sendMessage(player, MeowUtilities.SUCCESSFUL, "Public: " + playerGhost.getPublicTeleportPoints().keySet().toString());
    }
}
