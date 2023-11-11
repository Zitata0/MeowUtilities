package com.Zitata.MeowUtilities.Teleport.Patterns;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {

    private Map<String, Integer> cooldown;

    public Cooldown(){

        this.cooldown = new HashMap<String, Integer>();
        this.cooldown.put("tp", 0);
        this.cooldown.put("tpa", 0);
        this.cooldown.put("suicide", 0);
        this.cooldown.put("back", 0);

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
