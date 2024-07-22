package com.zitata.meowutilities.teleport;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.util.MessageSender;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeleportDelay extends Point {
    public enum Target {
        TELEPORT_POINT, PLAYER, OTHER, TELEPORT_PUBLIC_POINT
    }

    public final EntityPlayerMP playerSource;
    public final Target targetType;
    private long timeStamp;
    private EntityPlayerMP playerTarget = null;
    private TeleportPoint teleportPoint = null;

    /**
     * @param playerSource sender of the command
     * @param point purpose of the command
     */
    public TeleportDelay(EntityPlayerMP playerSource, Point point) {
        super(point.getDimension(), point.getX(), point.getY(), point.getZ(), point.getRotationYawHead(), point.getRotationPitch());
        if (playerSource == null) {
            throw new NullPointerException();
        }
        targetType = Target.OTHER;
        this.playerSource = playerSource;
        teleportDelay();
    }

    /**
     * @param playerSource sender of the command
     * @param playerTarget purpose of the command
     */
    public TeleportDelay(EntityPlayerMP playerSource, EntityPlayerMP playerTarget) {
        super(playerTarget.dimension, playerTarget.posX, playerTarget.posY, playerTarget.posZ, playerTarget.rotationYawHead, playerTarget.rotationPitch);
        if (playerSource == null) {
            throw new NullPointerException();
        }
        targetType = Target.PLAYER;
        this.playerSource = playerSource;
        this.playerTarget = playerTarget;
        teleportDelay();
    }

    /**
     * @param playerSource sender of the command
     * @param teleportPoint purpose of the command
     */
    public TeleportDelay(EntityPlayerMP playerSource, TeleportPoint teleportPoint) {
        super(teleportPoint.getDimension(), teleportPoint.getX(), teleportPoint.getY(), teleportPoint.getZ(), teleportPoint.getRotationYawHead(), teleportPoint.getRotationPitch());
        if (playerSource == null) {
            throw new NullPointerException();
        }
        if (MeowUtilities.playerList.get(playerSource.getDisplayName()).teleportPoints.containsValue(teleportPoint)) {
            targetType = Target.TELEPORT_POINT;
        } else {
            targetType = Target.TELEPORT_PUBLIC_POINT;
        }
        this.playerSource = playerSource;
        this.teleportPoint = teleportPoint;
        teleportDelay();
    }

    private void teleportDelay() {
        setTimeStamp();
        MessageSender.sendMessage(playerSource, MeowUtilities.PASSIVE, "You will be teleported in " + MeowUtilities.config.getTeleportDelay() / 1000 + " seconds");
        MeowUtilities.teleportDelayList.remove(playerSource);
    }

    public static void removeDelays(Map<EntityPlayerMP, TeleportDelay> teleportDelayMap, TeleportPoint teleportPoint) {
        List<EntityPlayerMP> players = new ArrayList<>();
        for (Map.Entry<EntityPlayerMP, TeleportDelay> teleportDelay : teleportDelayMap.entrySet()) {
            if (teleportDelay.getValue().targetType == TeleportDelay.Target.TELEPORT_POINT &&
                    teleportDelay.getValue().getTeleportPoint().equals(teleportPoint)) {
                players.add(teleportDelay.getKey());
            }
        }
        for (EntityPlayerMP player : players) {
            teleportDelayMap.remove(player);
            MessageSender.sendMessage(player, MeowUtilities.ERROR, "The teleportation to " + teleportPoint.getName() + " was interrupted");
        }
    }

    public EntityPlayerMP getPlayerTarget() {
        return playerTarget;
    }

    public TeleportPoint getTeleportPoint() {
        return teleportPoint;
    }

    /**
     * @return status of end delay
     */
    public boolean isDelay() {
        return System.currentTimeMillis() >= timeStamp;
    }

    /**
     * @return timestamp of end delay in ms
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp() {
        this.timeStamp = System.currentTimeMillis() + MeowUtilities.config.getTeleportDelay();
    }
}
