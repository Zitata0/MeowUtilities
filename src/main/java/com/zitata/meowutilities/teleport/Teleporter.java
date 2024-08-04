package com.zitata.meowutilities.teleport;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class Teleporter {
    public void teleportTo(TeleportDelay teleportDelay) {

        EntityPlayerMP playerSource = teleportDelay.playerSource;
        EntityPlayerMP playerTarget = teleportDelay.getPlayerTarget();

        Cooldown cooldown = MeowUtilities.playerList.get(playerSource.getDisplayName()).getCooldown();

        if (cooldown == null) {
            throw new NullPointerException();
        }

        if (playerSource.dimension != teleportDelay.getDimension()) {
            playerSource.travelToDimension(teleportDelay.getDimension());
        }

        switch (teleportDelay.targetType) {
            case TELEPORT_POINT: {
                playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), teleportDelay.getRotationYawHead(), teleportDelay.getRotationPitch());
                cooldown.setTp();
                MessageSender.sendMessage(playerSource, MessageSender.SUCCESSFUL, "You has been teleported to " + teleportDelay.getTeleportPoint().getName());
                break;
            }
            case PLAYER: {
                playerSource.playerNetServerHandler.setPlayerLocation(playerTarget.posX, playerTarget.posY, playerTarget.posZ, playerTarget.rotationYawHead, playerTarget.rotationPitch);
                cooldown.setTpa();
                MessageSender.sendMessage(playerSource, MessageSender.SUCCESSFUL, "You have been teleported to " + playerTarget.getDisplayName());
                MessageSender.sendMessage(playerTarget, MessageSender.SUCCESSFUL, playerSource.getDisplayName() + " was teleported to you");
                break;
            }
            case OTHER: {
                playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), teleportDelay.getRotationYawHead(), teleportDelay.getRotationPitch());
                cooldown.setBack(); // TODO Сделать телепортацию на последнюю точку смерти
                cooldown.setSpawn(); // TODO Сделать серверные точки телепортации или варпы
                break;
            }
            case TELEPORT_PUBLIC_POINT: {
                playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), teleportDelay.getRotationYawHead(), teleportDelay.getRotationPitch());
                cooldown.setTpPublic();
                MessageSender.sendMessage(playerSource, MessageSender.SUCCESSFUL, "You has been teleported to " + teleportDelay.getTeleportPoint().getName());
                break;
            }
        }
        MeowUtilities.teleportDelayList.remove(teleportDelay.playerSource);
    }
}
