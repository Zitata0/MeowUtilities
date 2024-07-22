package com.zitata.meowutilities.commands;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.util.MessageSender;
import com.zitata.meowutilities.entity.PlayerGhost;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class Suicide extends CommandBase {
    @Override
    public String getCommandName() {
        return "suicide";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/suicide";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (playerGhost.getCooldown().isSuicide()) {
            player.attackEntityFrom(DamageSource.generic.setDamageAllowedInCreativeMode().setDamageIsAbsolute(), Float.MAX_VALUE);
            playerGhost.getCooldown().setSuicide();
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "Think about kittens");
        } else {
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "Suicide will recharge in " + ((playerGhost.getCooldown().getSuicide() - System.currentTimeMillis()) / 1000) + " seconds");
        }
    }
}
