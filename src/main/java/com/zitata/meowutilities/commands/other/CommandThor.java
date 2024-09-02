package com.zitata.meowutilities.commands.other;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;

public class CommandThor extends CommandBase {

    @Override
    public String getCommandName() {
        return "thor";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/thor";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP playerMP = (EntityPlayerMP)sender;
        double x = playerMP.posX;
        double y = playerMP.posY;
        double z = playerMP.posZ;
        int dimension = playerMP.dimension;
        playerMP.mcServer.getConfigurationManager().sendToAllNear(x, y, z, 512.0D, dimension, new S2CPacketSpawnGlobalEntity(new EntityLightningBolt(playerMP.getEntityWorld(), x, y, z)));
    }
}
