package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.TeleportDelay;
import com.Zitata.MeowUtilities.TpaRequest;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nullable;

public class CommandTpaccept extends CommandBase {

    @Override
    public String getCommandName() {
        return ".tpaccept";
    }

    @Nullable
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

        //Проверка отправителя
        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;

        int removeIndexRequest = -1;
        for (TpaRequest tpaRequest : MeowUtilities.tpaRequests){
            if (tpaRequest.getTarget().equals(entityPlayerMP)){

                //Сообщение отправителю
                ChatComponentText message = new ChatComponentText("You will be teleported in 5 seconds");
                message.getChatStyle().setColor(MeowUtilities.PASSIVE);
                tpaRequest.getSender().addChatMessage(message);

                boolean hasDelay = false;
                for (TeleportDelay teleportDelay : MeowUtilities.teleportDelays){
                    if ((teleportDelay.getTarget() != null) && teleportDelay.getTarget().equals(tpaRequest.getTarget()) && teleportDelay.getSender().equals(tpaRequest.getSender())){
                        MeowUtilities.teleportDelays.remove(teleportDelay);
                        MeowUtilities.teleportDelays.add(new TeleportDelay(tpaRequest.getSender(), tpaRequest.getTarget())); //Добавление в список телепортации с задержкой
                        hasDelay = true;
                    }
                }
                if (!hasDelay){
                    MeowUtilities.teleportDelays.add(new TeleportDelay(tpaRequest.getSender(), tpaRequest.getTarget())); //Добавление в список телепортации с задержкой
                }

                removeIndexRequest = MeowUtilities.tpaRequests.indexOf(tpaRequest);
            }
        }

        if (removeIndexRequest > -1){
            MeowUtilities.tpaRequests.remove(removeIndexRequest);
        }else{
            //Отправка сообщения об отсутствии запросов
            ChatComponentText message = new ChatComponentText("You do not have a teleport request");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
