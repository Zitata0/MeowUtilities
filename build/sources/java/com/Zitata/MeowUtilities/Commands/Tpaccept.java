package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.TeleportDelay;
import com.Zitata.MeowUtilities.Teleport.TpaRequest;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class Tpaccept extends CommandBase {

    @Override
    public String getCommandName() {
        return ".tpaccept";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.tpaccept";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP playerTarget = (EntityPlayerMP) sender;

        int removeIndexRequest = -1;
        for (TpaRequest tpaRequest : MeowUtilities.tpaRequests){
            if (tpaRequest.getPlayerTarget().equals(playerTarget)){
                MeowUtilities.teleportDelays.add(new TeleportDelay(tpaRequest.getPlayerSource(), tpaRequest.getPlayerTarget()));
                removeIndexRequest = MeowUtilities.tpaRequests.indexOf(tpaRequest);
            }
        }

        if (removeIndexRequest > -1){
            MeowUtilities.tpaRequests.remove(removeIndexRequest);
        }else{
            ChatComponentText message = new ChatComponentText("You do not have a teleport request");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            playerTarget.addChatMessage(message);
        }
    }
}
