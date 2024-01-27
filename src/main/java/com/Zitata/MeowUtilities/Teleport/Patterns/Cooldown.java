package com.Zitata.MeowUtilities.Teleport.Patterns;

import com.Zitata.MeowUtilities.MeowUtilities;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {

    private String name;
    private Map<String, Long> cooldown;

    public Cooldown(String name){

        //Player name
        this.name = name;

        //Default values
        this.cooldown = new HashMap<String, Long>();
        this.cooldown.put("tp", 0L);
        this.cooldown.put("tpa", 0L);
        this.cooldown.put("suicide", 0L);
        this.cooldown.put("back", 0L);
    }

    /** @return status of end cooldown */
    public boolean isTp(){
        return System.currentTimeMillis() >= getTp();
    }

    /** @return status of end cooldown */
    public boolean isTpa(){
        return System.currentTimeMillis() >= getTpa();
    }

    /** @return status of end cooldown */
    public boolean isSuicide(){
        return System.currentTimeMillis() >= getSuicide();
    }

    /** @return status of end cooldown */
    public boolean isBack(){
        return System.currentTimeMillis() >= getBack();
    }

    /** @return timestamp of end cooldown in ms */
    public long getTp() {
        return cooldown.get("tp");
    }

    /** @return timestamp of end cooldown in ms */
    public long getTpa() {
        return cooldown.get("tpa");
    }

    /** @return timestamp of end cooldown in ms */
    public long getSuicide() {
        return cooldown.get("suicide");
    }

    /** @return timestamp of end cooldown in ms */
    public long getBack() {
        return cooldown.get("back");
    }

    /** @return player name */
    public String getName(){
        return name;
    }

    /** @param name player name */
    public void setName(String name){
        this.name = name;
    }

    public void setTp() {
        this.cooldown.put("tp", System.currentTimeMillis() + MeowUtilities.config.getTpCooldown());
    }

    public void setTpa() {
        this.cooldown.put("tpa", System.currentTimeMillis() + MeowUtilities.config.getTpaCooldown());
    }

    public void setSuicide() {
        this.cooldown.put("suicide", System.currentTimeMillis() + MeowUtilities.config.getSuicideCooldown());
    }

    public void setBack() {
        this.cooldown.put("back", System.currentTimeMillis() + MeowUtilities.config.getBackCooldown());
    }
}
