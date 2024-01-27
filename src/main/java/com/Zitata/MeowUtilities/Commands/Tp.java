package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
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

        Cooldown cooldown = null;
        for (Cooldown cooldownSelected : MeowUtilities.cooldowns){
            if (cooldownSelected.getName().equals(entityPlayerMP.getDisplayName())){
                cooldown = cooldownSelected;
                break;
            }
        }

        if (cooldown == null){
            System.out.println("Error: Cooldown");
            return;
        }

        if (!cooldown.isTp()){
            message = new ChatComponentText("Tp will recharge in " + ((cooldown.getTp() - System.currentTimeMillis()) / 1000) + " seconds");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        if (args.length > 1){
            publicTp(entityPlayerMP, args[1], args[0]);
        }else if (args.length > 0){
            privateTp(entityPlayerMP, args[0]);
        }else {
            privateTp(entityPlayerMP, "home");
        }
    }

    private void publicTp(EntityPlayerMP player, String teleportPointName, String teleportTargetName){
        ChatComponentText message;

        if (MeowUtilities.teleportPoints.get(teleportTargetName).isEmpty()){
            message = new ChatComponentText(teleportPointName + " do not have teleport points");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            player.addChatMessage(message);
            return;
        }

        TeleportPoint selectedTeleportPoint = null;
        for (TeleportPoint teleportPoint : MeowUtilities.teleportPoints.get(player.getDisplayName())){
            if (teleportPoint.getName().equals(teleportPointName)){
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

        if (!selectedTeleportPoint.isPublicPoint()){
            message = new ChatComponentText("Teleport point is not public");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            player.addChatMessage(message);
            return;
        }

        MeowUtilities.teleportDelays.add(new TeleportDelay(player, selectedTeleportPoint));
    }

    private void privateTp(EntityPlayerMP player, String teleportPointName){
        ChatComponentText message;

        if (MeowUtilities.teleportPoints.get(player.getDisplayName()).isEmpty()){
            message = new ChatComponentText("You do not have teleport points");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            player.addChatMessage(message);
            return;
        }

        TeleportPoint selectedTeleportPoint = null;
        for (TeleportPoint teleportPoint : MeowUtilities.teleportPoints.get(player.getDisplayName())){
            if (teleportPoint.getName().equals(teleportPointName)){
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

        MeowUtilities.teleportDelays.add(new TeleportDelay(player, selectedTeleportPoint));
    }
}
