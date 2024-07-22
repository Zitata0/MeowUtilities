package com.zitata.meowutilities.entity;

import com.zitata.meowutilities.teleport.Cooldown;
import com.zitata.meowutilities.teleport.TeleportPoint;

import java.util.HashMap;
import java.util.Map;

public class PlayerGhost {
    private Cooldown cooldown = new Cooldown();
    public Map<String, TeleportPoint> teleportPoints = new HashMap<>();

    /**
     * @return true, if player has public teleport point
     */
    public boolean hasPublicTeleportPoint() {
        for (TeleportPoint teleportPoint : teleportPoints.values()) {
            if (teleportPoint.isPublic()) {
                return true;
            }
        }
        return false;
    }

    public Map<String, TeleportPoint> getPublicTeleportPoints() {
        Map<String, TeleportPoint> teleportPoints = new HashMap<>();
        for (Map.Entry<String, TeleportPoint> teleportPoint : this.teleportPoints.entrySet()) {
            if (teleportPoint.getValue().isPublic()) {
                teleportPoints.put(teleportPoint.getKey(), teleportPoint.getValue());
            }
        }
        return teleportPoints;
    }

    public int publicTeleportPointCount() {
        int count = 0;
        for (TeleportPoint teleportPoint : teleportPoints.values()) {
            if (teleportPoint.isPublic()) {
                count++;
            }
        }
        return count;
    }

    public boolean hasTeleportPoint(String teleportPointName) {
        return teleportPoints.containsKey(teleportPointName);
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public void setCooldown(Cooldown cooldown) {
        this.cooldown = cooldown;
    }
}