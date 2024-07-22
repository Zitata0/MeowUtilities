package com.zitata.meowutilities.teleport;

public class TeleportPoint extends Point {
    private boolean isPublic = false;
    private final String teleportPointName;

    public TeleportPoint(String teleportPointName, int dimension, double x, double y, double z, float rotationYawHead, float rotationPitch) {
        super(dimension, x, y, z, rotationYawHead, rotationPitch);
        this.teleportPointName = teleportPointName;
    }

    public String getName() {
        return teleportPointName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
