package com.zitata.meowutilities.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CustomTeleporter extends Teleporter {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public CustomTeleporter(WorldServer worldServer, double x, double y, double z, float yaw, float pitch) {
        super(worldServer);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float yaw) {
        entity.setLocationAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        entity.motionX = 0D;
        entity.motionY = 0D;
        entity.motionZ = 0D;
        entity.fallDistance = 0F;
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float yaw) {
        return false;
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }
}
