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

public class Tp extends CommandBase {

    @Override
    public String getCommandName() {
        return ".tp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.tp [null/name]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getCommandAliases() {

        List<String> list = new ArrayList<String>();
        list.add(".home");

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

        TeleportPoint selectedTeleportPoint = null;
        for (TeleportPoint teleportPoint : teleportPoints){
            if (teleportPoint.getName().equals(teleportPointName)){
                selectedTeleportPoint = teleportPoint;
                break;
            }
        }
        if (selectedTeleportPoint != null){
            MeowUtilities.teleportDelays.add(new TeleportDelay(entityPlayerMP, selectedTeleportPoint));
        }else{
            message = new ChatComponentText("Teleport point not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
