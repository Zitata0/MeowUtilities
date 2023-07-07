package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nullable;

public class CommandSetHome extends CommandBase {

    @Override
    public String getCommandName() {
        return ".sethome";
    }

    @Nullable
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.sethome [null/name]";
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

        //Ограничение на установку в мирах
        if (entityPlayerMP.dimension != 0 || (entityPlayerMP.dimension >= 400 && entityPlayerMP.dimension <= 500)){
            ChatComponentText message = new ChatComponentText("You do not have permission to create a home in this world");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        //Название дома - "home", если название не указано
        if (args.length < 1){
            MeowUtilities.dataStorage.addHome(entityPlayerMP, "home",entityPlayerMP.dimension,entityPlayerMP.posX,entityPlayerMP.posY,entityPlayerMP.posZ);
        }else{
            MeowUtilities.dataStorage.addHome(entityPlayerMP, args[0],entityPlayerMP.dimension,entityPlayerMP.posX,entityPlayerMP.posY,entityPlayerMP.posZ);
        }
    }
}
