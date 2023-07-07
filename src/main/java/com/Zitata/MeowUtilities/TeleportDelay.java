package com.Zitata.MeowUtilities;

import net.minecraft.entity.player.EntityPlayerMP;

public class TeleportDelay {

    private EntityPlayerMP entityPlayerMPSender;
    private EntityPlayerMP entityPlayerMPTarget;
    private long timeStamp;

    private float x, y, z;
    private int dimension;
    private String home;

    public TeleportDelay(EntityPlayerMP entityPlayerMPSender, String home, int dimension, float x, float y, float z){
        this.entityPlayerMPSender = entityPlayerMPSender;
        this.timeStamp = System.currentTimeMillis();

        this.home = home;
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TeleportDelay(EntityPlayerMP entityPlayerMPSender, EntityPlayerMP entityPlayerMPTarget){
        this.entityPlayerMPSender = entityPlayerMPSender;
        this.entityPlayerMPTarget = entityPlayerMPTarget;
        this.timeStamp = System.currentTimeMillis();
    }

    public EntityPlayerMP getSender(){
        return this.entityPlayerMPSender;
    }

    public EntityPlayerMP getTarget(){
        return this.entityPlayerMPTarget;
    }

    public long getTimeStamp(){
        return this.timeStamp;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public float getZ(){
        return this.z;
    }

    public int getDimension(){
        return this.dimension;
    }

    public String getHome(){
        return this.home;
    }
}
