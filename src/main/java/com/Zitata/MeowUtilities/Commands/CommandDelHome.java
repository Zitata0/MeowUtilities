package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nullable;

public class CommandDelHome extends CommandBase {
    @Override
    public String getCommandName() {
        return ".delhome";
    }

    @Nullable
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.delhome [null/name]";
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
            MeowUtilities.dataStorage.delHome(entityPlayerMP, "home");
        }else{
            MeowUtilities.dataStorage.delHome(entityPlayerMP, args[0]);
        }
    }
}
