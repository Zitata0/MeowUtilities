package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import com.Zitata.MeowUtilities.Teleport.TeleportDelay;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import java.util.ArrayList;
import java.util.List;

public class SetTp extends CommandBase {

    @Override
    public String getCommandName() {
        return ".stp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.stp [null/name]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getCommandAliases() {

        List<String> list = new ArrayList<String>();
        list.add(".sethome");
        list.add(".settp");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (!(sender instanceof EntityPlayerMP)) {
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
        ChatComponentText message;

        if (!((entityPlayerMP.dimension == 0) || (entityPlayerMP.dimension >= 400 && entityPlayerMP.dimension <= 500))){
            message = new ChatComponentText("You do not have permission to create a home in this world");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        List<TeleportPoint> teleportPoints = MeowUtilities.teleportPoints.get(entityPlayerMP.getDisplayName());

        String teleportPointName;
        if (args.length < 1){
            teleportPointName = "home";
        }else{
            teleportPointName = args[0];
        }

        TeleportPoint selectedTeleportPoint = null;
        for (TeleportPoint teleportPoint : teleportPoints){
            if (teleportPoint.getName().equals(teleportPointName)){
                selectedTeleportPoint = teleportPoint;
                break;
            }
        }
        if (selectedTeleportPoint != null){
            selectedTeleportPoint.setDimension(entityPlayerMP.dimension);
            selectedTeleportPoint.setX(entityPlayerMP.posX);
            selectedTeleportPoint.setY(entityPlayerMP.posY);
            selectedTeleportPoint.setZ(entityPlayerMP.posZ);

            message = new ChatComponentText("The \"" + teleportPointName + "\" has been modified");
            message.getChatStyle().setColor(MeowUtilities.PASSIVE);
            entityPlayerMP.addChatMessage(message);

            int removeIndex = -1;
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
        }else if (teleportPoints.size() >= MeowUtilities.config.getTpCount()){
            message = new ChatComponentText("You have a lot of teleport points");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }else{
            teleportPoints.add(new TeleportPoint(teleportPointName, entityPlayerMP.dimension, entityPlayerMP.posX, entityPlayerMP.posY, entityPlayerMP.posZ, false));

            message = new ChatComponentText("The \"" + teleportPointName + "\" has been added");
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
