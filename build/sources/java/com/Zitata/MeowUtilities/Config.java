package com.Zitata.MeowUtilities;

public class Config {

    private int teleportDelay;
    private int homeCooldown;
    private int tpaCooldown;
    private int suicideCooldown;
    private int homeCount;

    public Config(int teleportDelay, int homeCooldown, int tpaCooldown, int suicideCooldown, int homeCount){
        this.teleportDelay = teleportDelay;
        this.homeCooldown = homeCooldown;
        this.tpaCooldown = tpaCooldown;
        this.suicideCooldown = suicideCooldown;
        this.homeCount = homeCount;
    }

    public int getTeleportDelay(){
        return this.teleportDelay;
    }
    public int getHomeCooldown(){
        return this.homeCooldown;
    }
    public int getTpaCooldown(){
        return this.tpaCooldown;
    }
    public int getSuicideCooldown(){
        return this.suicideCooldown;
    }
    public int getHomeCount(){
        return this.homeCount;
    }
}
