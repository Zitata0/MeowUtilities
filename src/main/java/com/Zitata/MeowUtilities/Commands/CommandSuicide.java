package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class CommandSuicide extends CommandBase {
    @Override
    public String getCommandName() {
        return ".suicide";
    }

    @Nullable
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.suicide";
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

        if (MeowUtilities.dataStorage.isCooldown(entityPlayerMP, "suicide")){

            //Сообщение игроку
            ChatComponentText message = new ChatComponentText("Suicide will recharge in " + MeowUtilities.dataStorage.cooldown + " seconds");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }else{

            //Перезарядка
            MeowUtilities.dataStorage.setCooldown(entityPlayerMP, "suicide");

            //Смерть
            entityPlayerMP.attackEntityFrom(DamageSource.generic.setDamageAllowedInCreativeMode().setDamageIsAbsolute(), entityPlayerMP.getMaxHealth());

            //Сообщение в чат игроку
            ChatComponentText message = new ChatComponentText("Think about kittens");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
