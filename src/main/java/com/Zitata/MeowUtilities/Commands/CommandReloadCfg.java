package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nullable;

public class CommandReloadCfg extends CommandBase {
    @Override
    public String getCommandName() {
        return ".reloadcfg";
    }

    @Nullable
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/.reloadcfg";
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

        //Обновление конфигурации
        MeowUtilities.config = MeowUtilities.dataStorage.getConfig();

        ChatComponentText message = new ChatComponentText("Configuration is reloaded");
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMP.addChatMessage(message);
    }
}
