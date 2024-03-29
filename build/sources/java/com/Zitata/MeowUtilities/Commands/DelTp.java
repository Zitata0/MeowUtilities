package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import com.Zitata.MeowUtilities.Teleport.TeleportDelay;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class DelTp extends CommandBase {
    @Override
    public String getCommandName() {
        return ".dtp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.dtp [null/name]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getCommandAliases() {

        List<String> list = new ArrayList<String>();
        list.add(".delhome");
        list.add(".deltp");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
        ChatComponentText message;

        if (!MeowUtilities.teleportPoints.containsKey(entityPlayerMP.getDisplayName())){
            message = new ChatComponentText("You do not have teleport points");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }

        List<TeleportPoint> teleportPoints = MeowUtilities.teleportPoints.get(entityPlayerMP.getDisplayName());
        String teleportPointName;

        if (args.length < 1){
            teleportPointName = "home";
        }else{
            teleportPointName = args[0];
        }

        int removeIndex = -1;
        for (TeleportPoint teleportPoint : teleportPoints){
            if (teleportPoint.getName().equals(teleportPointName)){
                removeIndex = teleportPoints.indexOf(teleportPoint);
                break;
            }
        }
        if (removeIndex > -1){
            teleportPoints.remove(removeIndex);

            message = new ChatComponentText("The \"" + teleportPointName + "\" has been removed");
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            entityPlayerMP.addChatMessage(message);

            removeIndex = -1;
            for (TeleportDelay teleportDelay : MeowUtilities.teleportDelays){
                if (teleportDelay.getPlayerSource() == entityPlayerMP && teleportDelay.getTeleportPoint().getName().equals(teleportPointName)){
                    removeIndex = MeowUtilities.teleportDelays.indexOf(teleportDelay);
                    break;
                }
            }
            if (removeIndex > -1){
                MeowUtilities.teleportDelays.remove(removeIndex);

                message = new ChatComponentText("The teleportation to " + teleportPointName + " was interrupted");
                message.getChatStyle().setColor(MeowUtilities.ERROR);
                entityPlayerMP.addChatMessage(message);
            }
        }else{
            message = new ChatComponentText("Teleport point not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
