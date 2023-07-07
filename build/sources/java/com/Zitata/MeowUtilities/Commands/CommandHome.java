package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import javax.annotation.Nullable;

public class CommandHome extends CommandBase {
    @Override
    public String getCommandName() {
        return ".home";
    }

    @Nullable
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.home [null/name]";
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

        //Название дома - "home", если название не указано
        if (args.length < 1){
            MeowUtilities.teleport.teleportToHome(entityPlayerMP, "home");
        }else{
            MeowUtilities.teleport.teleportToHome(entityPlayerMP, args[0]);
        }
    }
}
