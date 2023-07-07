package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.TpaRequest;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nullable;
import java.util.List;

public class CommandTpa extends CommandBase {

    @Override
    public String getCommandName() {
        return ".tpa";
    }

    @Nullable
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

        //Проверка отправителя
        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;

        //использование команды без аргумента
        if (args.length < 1){
            ChatComponentText message = new ChatComponentText("Player does not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        EntityPlayerMP entityPlayerMPRequest = null;

        //Поиск вызываемого игрока на сервере
        boolean hasPlayer = false;
        for (EntityPlayerMP entityPlayerMPRequestTemp : entityPlayerMP.mcServer.getConfigurationManager().playerEntityList){
            if (entityPlayerMPRequestTemp.getDisplayName().equals(args[0])){
                entityPlayerMPRequest = entityPlayerMPRequestTemp;

                //Проверка перезарядки
                if (MeowUtilities.dataStorage.isCooldown(entityPlayerMP, "tpa")){
                    ChatComponentText message = new ChatComponentText("Tpa will recharge in " + MeowUtilities.dataStorage.cooldown + " seconds");
                    message.getChatStyle().setColor(MeowUtilities.PASSIVE);
                    entityPlayerMP.addChatMessage(message);
                    return;
                }

                //Добавление запроса в список запросов
                boolean hasTpaRequest = false;
                for (TpaRequest tpaRequest : MeowUtilities.tpaRequests){
                    if (tpaRequest.getTarget().equals(entityPlayerMPRequest)){
                        MeowUtilities.tpaRequests.remove(tpaRequest);
                        MeowUtilities.tpaRequests.add(new TpaRequest(entityPlayerMP, entityPlayerMPRequest));
                        hasTpaRequest = true;
                    }
                }

                if (!hasTpaRequest){
                    MeowUtilities.tpaRequests.add(new TpaRequest(entityPlayerMP, entityPlayerMPRequest));
                }

                hasPlayer = true;
            }
        }
        if (!hasPlayer){
            ChatComponentText message = new ChatComponentText("Player does not found");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        //Отправка сообщение запросившему игроку
        ChatComponentText message = new ChatComponentText("Request has been sent to " + entityPlayerMPRequest.getDisplayName());
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        entityPlayerMP.addChatMessage(message);

        //Отправка сообщения вызываемому игроку
        message = new ChatComponentText(entityPlayerMP.getDisplayName() + " wants to teleport to you");
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        entityPlayerMPRequest.addChatMessage(message);
        message = new ChatComponentText("Use \"/.tpaccept\" to accept teleport");
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMPRequest.addChatMessage(message);
    }
}
