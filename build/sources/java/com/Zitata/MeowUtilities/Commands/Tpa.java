package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
import com.Zitata.MeowUtilities.Teleport.TpaRequest;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import java.util.List;

public class Tpa extends CommandBase {

    @Override
    public String getCommandName() {
        return ".tpa";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.tpa [name]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayerMP)){
            return null;
        }
        if (args.length == 1){
            return CommandBase.getListOfStringsMatchingLastWord(args, ((EntityPlayerMP) sender).mcServer.getAllUsernames());
        }else {
            return null;
        }
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP playerSource = (EntityPlayerMP) sender;
        ChatComponentText message;

        Cooldown cooldown = null;
        for (Cooldown cooldownSelected : MeowUtilities.cooldowns){
            if (cooldownSelected.getName().equals(playerSource.getDisplayName())){
                cooldown = cooldownSelected;
                break;
            }
        }

        if (cooldown == null){
            System.out.println("Error: Cooldown");
            return;
        }

        if (!cooldown.isTp()){
            message = new ChatComponentText("Tpa will recharge in " + ((cooldown.getTpa() - System.currentTimeMillis()) / 1000) + " seconds");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            playerSource.addChatMessage(message);
            return;
        }

        if (args.length < 1){
            message = new ChatComponentText("Player not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            playerSource.addChatMessage(message);
            return;
        }

        EntityPlayerMP playerTarget = null;

        boolean hasPlayer = false;
        for (EntityPlayerMP entityPlayerMPRequestTemp : playerSource.mcServer.getConfigurationManager().playerEntityList){
            if (entityPlayerMPRequestTemp.getDisplayName().equals(args[0])){
                playerTarget = entityPlayerMPRequestTemp;

                boolean hasTpaRequest = false;
                for (TpaRequest tpaRequest : MeowUtilities.tpaRequests){
                    if (tpaRequest.getPlayerTarget().equals(playerTarget)){
                        MeowUtilities.tpaRequests.remove(tpaRequest);
                        MeowUtilities.tpaRequests.add(new TpaRequest(playerSource, playerTarget));
                        hasTpaRequest = true;
                    }
                }

                if (!hasTpaRequest){
                    MeowUtilities.tpaRequests.add(new TpaRequest(playerSource, playerTarget));
                }

                hasPlayer = true;
            }
        }
        if (!hasPlayer){
            message = new ChatComponentText("Player not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            playerSource.addChatMessage(message);
            return;
        }

        message = new ChatComponentText("Request has been sent to " + playerTarget.getDisplayName());
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        playerSource.addChatMessage(message);

        message = new ChatComponentText(playerSource.getDisplayName() + " wants to teleport to you");
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        playerTarget.addChatMessage(message);

        message = new ChatComponentText("Use \"" + "/.tpaccept" + "\" to accept teleport");
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        playerTarget.addChatMessage(message);
    }
}
