package com.zitata.meowutilities.commands.other;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;
import com.zitata.meowutilities.teleport.Point;
import com.zitata.meowutilities.teleport.TeleportDelay;
import com.zitata.meowutilities.teleport.TeleportPoint;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;

public class CommandSpawn extends CommandBase {
    @Override
    public String getCommandName() {
        return "spawn";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/spawn";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayerMP;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP)sender;
        PlayerGhost playerGhost = MeowUtilities.playerList.get(player.getDisplayName());

        if (!playerGhost.getCooldown().isSpawn()) {
            MessageSender.sendMessage(player, MessageSender.ERROR, String.format("Spawn will recharge in %s seconds", ((playerGhost.getCooldown().getSpawn() - System.currentTimeMillis()) / 1000)));
            return;
        }

        ChunkCoordinates spawnPoint = player.mcServer.getEntityWorld().getSpawnPoint();
        TeleportPoint spawn = new TeleportPoint("spawn", 0, spawnPoint.posX, spawnPoint.posY, spawnPoint.posZ, player.rotationYawHead, player.rotationPitch);
        MeowUtilities.teleportDelayList.put(player, new TeleportDelay(player, (Point)spawn));
    }
}
