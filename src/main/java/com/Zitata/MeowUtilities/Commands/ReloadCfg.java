package com.Zitata.MeowUtilities.Commands;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class ReloadCfg extends CommandBase {
    @Override
    public String getCommandName() {
        return ".reloadcfg";
    }

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

        if (!(sender instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;

        MeowUtilities.config.readConfig();

        ChatComponentText message = new ChatComponentText("Configuration is reloaded");
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMP.addChatMessage(message);
    }
}
