package com.Zitata.MeowUtilities.Data;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.*;

public class Data {

    private final Gson gson;
    private final String dirPath;
    public final String configFileName;
    public final String cooldownFileName;
    public final String teleportPointsFileName;

    public Data(){
        this.dirPath = "." + File.separator + MeowUtilities.modName;
        gson = new Gson();

        File file = new File(this.dirPath);
        if (!file.exists()){
            if (file.mkdir()){
                System.out.println("File \"" + file.getName() + "\" has been created");
            }else {
                System.out.println("Error: File " + file.getName() + " was not created");
            }
        }

        this.configFileName = "config.json";
        this.cooldownFileName = "cooldown.json";
        this.teleportPointsFileName = "teleportPoints.json";

        createFile(configFileName);
        createFile(cooldownFileName);
        createFile(teleportPointsFileName);
    }

    public void saveCooldown(){
        writeJson(MeowUtilities.cooldowns, cooldownFileName);
    }

    public void readCooldown(){
        String str = read(cooldownFileName);
        MeowUtilities.cooldowns = gson.fromJson(str, MeowUtilities.cooldowns.getClass());
    }

    public void saveTeleportPoints(){
        writeJson(MeowUtilities.teleportPoints, teleportPointsFileName);
    }

    public void readTeleportPoints(){
        JsonObject jsonObject = new JsonParser().parse(read(teleportPointsFileName)).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
            List<TeleportPoint> teleportPoints = new ArrayList<TeleportPoint>();
            for (JsonElement je : entry.getValue().getAsJsonArray()){
                teleportPoints.add(gson.fromJson(je, TeleportPoint.class));
            }
            MeowUtilities.teleportPoints.put(entry.getKey(), teleportPoints);
        }
    }

    public void createFile(String fileName){
        File file = new File(this.dirPath, fileName);
        if (!file.exists()){
            try {
                if (file.createNewFile()){
                    System.out.println("File \"" + file.getName() + "\" has been created");
                }else{
                    System.out.println("Error: File " + file.getName() + " was not created");
                }
            } catch (IOException e) {
                System.out.println("Error: File \"" + file.getName() + "\" was not created");
                e.printStackTrace();
            }
        }
    }

    public void write(String str, String fileName){
        try{
            PrintWriter out = new PrintWriter(new FileWriter(this.dirPath + File.separator + fileName));
            out.write(str);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read(String fileName){

        File file = new File(dirPath, fileName);

        if (!file.exists()){
            return "";
        }

        String str = "";

        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("/");
            str = scanner.next();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + fileName + "\" not found");
        } catch (NoSuchElementException e){
            System.out.println("File \"" + fileName + "\" do not have a line");
        }

        return str;
    }

    public void writeJson(Object obj, String fileName){

        if (!new File(this.dirPath, fileName).exists()){
            return;
        }

        try{
            PrintWriter out = new PrintWriter(new FileWriter(this.dirPath + File.separator + fileName));
            out.write(gson.toJson(obj));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
