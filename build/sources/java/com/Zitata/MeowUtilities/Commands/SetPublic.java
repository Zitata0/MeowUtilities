package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class SetPublic extends CommandBase {

    @Override
    public String getCommandName() {
        return ".sp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.sp [name]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {

        List<String> list = new ArrayList<String>();
        list.add(".setpublic");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (!(sender instanceof EntityPlayerMP)) {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) sender;
        ChatComponentText message;

        if (args.length < 1){
            getCommandUsage(sender);
            return;
        }

        if (MeowUtilities.teleportPoints.get(player.getDisplayName()).size() == 0){
            message = new ChatComponentText("You do not have teleport points");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            player.addChatMessage(message);
            return;
        }

        TeleportPoint selectedTeleportPoint = null;
        for (TeleportPoint teleportPoint : MeowUtilities.teleportPoints.get(player.getDisplayName())){
            if (teleportPoint.getName().equals(args[0])){
                selectedTeleportPoint = teleportPoint;
                break;
            }
        }

        if (selectedTeleportPoint == null){
            message = new ChatComponentText("Teleport point not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            player.addChatMessage(message);
            return;
        }

        if (selectedTeleportPoint.isPublicPoint()){
            selectedTeleportPoint.setPublicPoint(false);
        }else{
            selectedTeleportPoint.setPublicPoint(true);
        }

        message = new ChatComponentText("The \"" + selectedTeleportPoint.getName() + "\" has been modified");
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        player.addChatMessage(message);
    }
}
