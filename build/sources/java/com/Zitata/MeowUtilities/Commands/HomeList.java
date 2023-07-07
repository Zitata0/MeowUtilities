package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HomeList extends CommandBase {

    @Override
    public String getCommandName() {
        return ".homelist";
    }

    @Nullable
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.homelist";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender){
        return true;
    }

    @Override
    public List getCommandAliases() {

        List<String> list = new ArrayList<String>();
        list.add(".homes");

        return list;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        //Проверка отправителя
        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;

        //Получение списка домов
        List<String> homeList = MeowUtilities.dataStorage.getHomeList(entityPlayerMP);

        if (homeList == null){

            ChatComponentText message = new ChatComponentText("Error: HomeList");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);

            return;
        }else if (homeList.isEmpty()){

            //Отправка списка точек домов
            ChatComponentText message = new ChatComponentText("You do not have homes");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);

            return;
        }

        //Отправка списка точек домов
        ChatComponentText message = new ChatComponentText("Homes (" + homeList.size() + "): " + homeList.toString());
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMP.addChatMessage(message);
    }
}
