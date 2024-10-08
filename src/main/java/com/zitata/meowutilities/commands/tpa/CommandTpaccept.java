package com.zitata.meowutilities.commands.tpa;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandTpaccept extends CommandBase {

    @Override
    public String getCommandName() {
        return "tpaccept";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/tpaccept";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP playerTarget = (EntityPlayerMP) sender;

        if (MeowUtilities.tpaRequestList.containsKey(playerTarget)) {
            MeowUtilities.teleportDelayList.put(MeowUtilities.tpaRequestList.get(playerTarget), new TeleportDelay(MeowUtilities.tpaRequestList.get(playerTarget), playerTarget));
            MessageSender.sendMessage(playerTarget, MessageSender.PASSIVE, String.format("%s will be teleported to you in %s seconds", MeowUtilities.tpaRequestList.get(playerTarget).getDisplayName(), MeowUtilities.config.getTeleportDelay() / 1000));
            MeowUtilities.tpaRequestList.remove(playerTarget);
        } else {
            MessageSender.sendMessage(playerTarget, MessageSender.ERROR, "You do not have a teleport request");
        }
    }
}
