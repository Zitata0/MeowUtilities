package com.Zitata.MeowUtilities.Data;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class CooldownData {

    private Map<String, Integer> cooldown;

    public CooldownData(){

        this.cooldown = new HashMap<String, Integer>();
        this.cooldown.put("tp", 5);
        this.cooldown.put("tpa", 300);
        this.cooldown.put("suicide", 600);
        this.cooldown.put("back", 1);

        readCooldown();
    }

    private void readCooldown(){
        String str = MeowUtilities.data.read(MeowUtilities.data.cooldownFileName);
        JsonObject cooldownFile = new JsonParser().parse(str).getAsJsonObject();

        for (Map.Entry<String, Integer> config : this.cooldown.entrySet()){
            if (cooldownFile.has(config.getKey())){
                config.setValue(cooldownFile.get(config.getKey()).getAsInt());
            }
        }
    }

    public int getTp() {
        return cooldown.get("tp");
    }

    public int getTpa() {
        return cooldown.get("tpa");
    }

    public int getSuicide() {
        return cooldown.get("suicide");
    }

    public int getBack() {
        return cooldown.get("back");
    }

    public void setTp(int cooldown) {
        this.cooldown.put("tp", cooldown);
    }

    public void setTpa(int cooldown) {
        this.cooldown.put("tpa", cooldown);
    }

    public void setSuicide(int cooldown) {
        this.cooldown.put("suicide", cooldown);
    }

    public void setBack(int cooldown) {
        this.cooldown.put("back", cooldown);
    }
}
