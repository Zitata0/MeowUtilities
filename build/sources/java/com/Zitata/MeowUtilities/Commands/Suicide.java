package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;

public class Suicide extends CommandBase {
    @Override
    public String getCommandName() {
        return ".suicide";
    }

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

        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
        ChatComponentText message;

        if (false){

            message = new ChatComponentText("Suicide will recharge in " + "" + " seconds");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }else{
            entityPlayerMP.attackEntityFrom(DamageSource.generic.setDamageAllowedInCreativeMode().setDamageIsAbsolute(), Float.MAX_VALUE);

            message = new ChatComponentText("Think about kittens");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
