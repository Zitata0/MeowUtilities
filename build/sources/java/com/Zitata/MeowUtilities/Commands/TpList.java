package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import java.util.ArrayList;
import java.util.List;

public class TpList extends CommandBase {

    @Override
    public String getCommandName() {
        return ".tpl";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.tpl";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getCommandAliases() {

        List<String> list = new ArrayList<String>();
        list.add(".homes");
        list.add(".homelist");
        list.add(".tplist");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
        ChatComponentText message;

        List<TeleportPoint> teleportPoints = MeowUtilities.teleportPoints.get(entityPlayerMP.getDisplayName());

        if (teleportPoints == null){
            message = new ChatComponentText("Error: HomeList");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);

            return;
        }else if (teleportPoints.isEmpty()){
            message = new ChatComponentText("You do not have homes");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);

            return;
        }

        List<String> teleportPointsName = new ArrayList<String>();
        for (TeleportPoint teleportPoint : teleportPoints){
            teleportPointsName.add(teleportPoint.getName());
        }

        message = new ChatComponentText("TeleportPoints (" + teleportPoints.size() + "): " + teleportPointsName.toString());
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMP.addChatMessage(message);
    }
}
