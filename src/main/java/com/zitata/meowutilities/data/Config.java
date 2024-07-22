package com.zitata.meowutilities.data;

public class Config {
    private int teleportDelay = 5;
    private int tpCooldown = 300;
    private int tpPublicCooldown = 600;
    private int tpaCooldown = 600;
    private int tpCount = 1;
    private int tpPublicCount = 0;
    private int suicideCooldown = 60;
    private int backCooldown = 300;
    private int dataSave = 300;

    /**
     * @return value in ms
     */
    public int getTpPublicCooldown() {
        return tpPublicCooldown * 1000;
    }

    /**
     * @return public teleport points max count
     */
    public int getTpPublicCount() {
        return tpPublicCount;
    }

    /**
     * @return value in ms
     */
    public int getTeleportDelay() {
        return teleportDelay * 1000;
    }

    /**
     * @return value in ms
     */
    public int getTpCooldown() {
        return tpCooldown * 1000;
    }

    /**
     * @return value in ms
     */
    public int getTpaCooldown() {
        return tpaCooldown * 1000;
    }

    /**
     * @return value in ms
     */
    public int getSuicideCooldown() {
        return suicideCooldown * 1000;
    }

    /**
     * @return teleport points max count
     */
    public int getTpCount() {
        return tpCount;
    }

    /**
     * @return value in ms
     */
    public int getBackCooldown() {
        return backCooldown * 1000;
    }

    /**
     * @return value in ms
     */
    public int getDataSave() {
        return dataSave * 1000;
    }

    /**
     * @param teleportDelay value in seconds
     */
    public void setTeleportDelay(int teleportDelay) {
        this.teleportDelay = teleportDelay;
    }

    /**
     * @param tpCooldown value in seconds
     */
    public void setTpCooldown(int tpCooldown) {
        this.tpCooldown = tpCooldown;
    }

    /**
     * @param tpaCooldown value in seconds
     */
    public void setTpaCooldown(int tpaCooldown) {
        this.tpaCooldown = tpaCooldown;
    }

    /**
     * @param suicideCooldown value in seconds
     */
    public void setSuicideCooldown(int suicideCooldown) {
        this.suicideCooldown = suicideCooldown;
    }

    /**
     * @param tpCount teleport points max count
     */
    public void setTpCount(int tpCount) {
        this.tpCount = tpCount;
    }

    /**
     * @param backCooldown value in seconds
     */
    public void setBackCooldown(int backCooldown) {
        this.backCooldown = backCooldown;
    }

    /**
     * @param dataSave value in seconds
     */
    public void setDataSave(int dataSave) {
        this.dataSave = dataSave;
    }

    /**
     * @param tpPublicCount public teleport points max count
     */
    public void setTpPublicCount(int tpPublicCount) {
        this.tpPublicCount = tpPublicCount;
    }

    /**
     * @param tpPublicCooldown value in seconds
     */
    public void setPublicCooldown(int tpPublicCooldown) {
        this.tpPublicCooldown = tpPublicCooldown;
    }
}
