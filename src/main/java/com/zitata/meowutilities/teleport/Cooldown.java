package com.zitata.meowutilities.teleport;

import com.zitata.meowutilities.MeowUtilities;

public class Cooldown {
    private long tp = 0L;
    private long tpa = 0L;
    private long suicide = 0L;
    private long back = 0L;

    /**
     * @return status of end cooldown
     */
    public boolean isTp() {
        return System.currentTimeMillis() >= getTp();
    }

    /**
     * @return status of end cooldown
     */
    public boolean isTpa() {
        return System.currentTimeMillis() >= getTpa();
    }

    /**
     * @return status of end cooldown
     */
    public boolean isSuicide() {
        return System.currentTimeMillis() >= getSuicide();
    }

    /**
     * @return status of end cooldown
     */
    public boolean isBack() {
        return System.currentTimeMillis() >= getBack();
    }

    /**
     * @return timestamp of end cooldown in ms
     */
    public long getTp() {
        return tp;
    }

    /**
     * @return timestamp of end cooldown in ms
     */
    public long getTpa() {
        return tpa;
    }

    /**
     * @return timestamp of end cooldown in ms
     */
    public long getSuicide() {
        return suicide;
    }

    /**
     * @return timestamp of end cooldown in ms
     */
    public long getBack() {
        return back;
    }

    /**
     * set timestamp of end cooldown
     */
    public void setTp() {
        tp = System.currentTimeMillis() + MeowUtilities.config.getTpCooldown();
    }

    /**
     * set timestamp of end cooldown
     */
    public void setTpa() {
        tpa = System.currentTimeMillis() + MeowUtilities.config.getTpaCooldown();
    }

    /**
     * set timestamp of end cooldown
     */
    public void setSuicide() {
        suicide = System.currentTimeMillis() + MeowUtilities.config.getSuicideCooldown();
    }

    /**
     * set timestamp of end cooldown
     */
    public void setBack() {
        back = System.currentTimeMillis() + MeowUtilities.config.getBackCooldown();
    }
}
