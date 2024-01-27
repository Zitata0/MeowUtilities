package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
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

        if (cooldown.isSuicide()){
            entityPlayerMP.attackEntityFrom(DamageSource.generic.setDamageAllowedInCreativeMode().setDamageIsAbsolute(), Float.MAX_VALUE);
            cooldown.setSuicide();

            message = new ChatComponentText("Think about kittens");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }else{
            message = new ChatComponentText("Suicide will recharge in " + ((cooldown.getSuicide() - System.currentTimeMillis()) / 1000) + " seconds");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
        }
    }
}
