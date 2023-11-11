package com.Zitata.MeowUtilities.Data;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Integer> config;

    public Config(){
        this.config = new HashMap<String, Integer>();
        this.config.put("teleportDelay", 5);
        this.config.put("tpCooldown", 300);
        this.config.put("tpaCooldown", 600);
        this.config.put("tpCount", 1);
        this.config.put("suicideCooldown", 60);
        this.config.put("backCooldown", 300);

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

    public int getTeleportDelay() {
        return config.get("teleportDelay");
    }

    public int getTpCooldown() {
        return config.get("tpCooldown");
    }

    public int getTpaCooldown() {
        return config.get("tpaCooldown");
    }

    public int getSuicideCooldown() {
        return config.get("suicideCooldown");
    }

    public int getTpCount() {
        return config.get("tpCount");
    }

    public int getBackCooldown() {
        return config.get("backCooldown");
    }

    public void setTeleportDelay(int teleportDelay) {
        this.config.put("teleportDelay", teleportDelay);
    }

    public void setTpCooldown(int tpCooldown) {
        this.config.put("tpCooldown", tpCooldown);
    }

    public void setTpaCooldown(int tpaCooldown) {
        this.config.put("tpaCooldown", tpaCooldown);
    }

    public void setSuicideCooldown(int suicideCooldown) {
        this.config.put("suicideCooldown", suicideCooldown);
    }

    public void setTpCount(int tpCount) {
        this.config.put("tpCount", tpCount);
    }

    public void setBackCooldown(int backCooldown) {
        this.config.put("backCooldown", backCooldown);
    }
}
