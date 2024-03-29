package com.Zitata.MeowUtilities.Data;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Integer> config;

    public Config(){

        //Default values
        this.config = new HashMap<String, Integer>();
        this.config.put("teleportDelay", 5);
        this.config.put("tpCooldown", 300);
        this.config.put("tpaCooldown", 600);
        this.config.put("tpCount", 1);
        this.config.put("suicideCooldown", 60);
        this.config.put("backCooldown", 300);
        this.config.put("dataSave", 300);

        readConfig();
    }

    public void readConfig(){
        String str = MeowUtilities.data.read(MeowUtilities.data.configFileName);
        JsonObject configFile;
        if (str.isEmpty()){
            configFile = new JsonObject();
        }else{
            configFile = new JsonParser().parse(str).getAsJsonObject();
        }

        for (Map.Entry<String, Integer> config : this.config.entrySet()){
            if (configFile.has(config.getKey())){
                config.setValue(configFile.get(config.getKey()).getAsInt());
            }
        }
    }

    public void saveConfig(){
        MeowUtilities.data.writeJson(config, MeowUtilities.data.configFileName);
    }

    /** @return - value in ms */
    public int getTeleportDelay() {
        return config.get("teleportDelay") * 1000;
    }

    /** @return - value in ms */
    public int getTpCooldown() {
        return config.get("tpCooldown") * 1000;
    }

    /** @return - value in ms */
    public int getTpaCooldown() {
        return config.get("tpaCooldown") * 1000;
    }

    /** @return - value in ms */
    public int getSuicideCooldown() {
        return config.get("suicideCooldown") * 1000;
    }

    /** @return - teleportPoints max count */
    public int getTpCount() {
        return config.get("tpCount");
    }

    /** @return - value in ms */
    public int getBackCooldown() {
        return config.get("backCooldown") * 1000;
    }

    /** @return - value in ms */
    public int getDataSave(){
        return config.get("dataSave") * 1000;
    }

    /** @param teleportDelay - value in seconds */
    public void setTeleportDelay(int teleportDelay) {
        config.put("teleportDelay", teleportDelay);
    }

    /** @param tpCooldown - value in seconds */
    public void setTpCooldown(int tpCooldown) {
        config.put("tpCooldown", tpCooldown);
    }

    /** @param tpaCooldown - value in seconds */
    public void setTpaCooldown(int tpaCooldown) {
        config.put("tpaCooldown", tpaCooldown);
    }

    /** @param suicideCooldown - value in seconds */
    public void setSuicideCooldown(int suicideCooldown) {
        config.put("suicideCooldown", suicideCooldown);
    }

    /** @param tpCount - value in seconds */
    public void setTpCount(int tpCount) {
        config.put("tpCount", tpCount);
    }

    /** @param backCooldown - value in seconds */
    public void setBackCooldown(int backCooldown) {
        config.put("backCooldown", backCooldown);
    }

    /** @param dataSave - value in seconds */
    public void setDataSave(int dataSave){
        config.put("dataSave", dataSave);
    }
}
