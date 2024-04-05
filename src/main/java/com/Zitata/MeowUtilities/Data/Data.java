package com.Zitata.MeowUtilities.Data;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import com.google.gson.*;
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

    public List<Cooldown> readCooldown(){
        return gson.fromJson(readJson(cooldownFileName).getAsJsonArray(), List.class);
    }

    public void saveTeleportPoints(){
        writeJson(MeowUtilities.teleportPoints, teleportPointsFileName);
    }

    public void readTeleportPoints(){
        JsonElement jsonElement = readJson(teleportPointsFileName);
        JsonObject jsonObject;
        if (jsonElement == null){
            jsonObject = new JsonObject();
        }else {
            jsonObject = jsonElement.getAsJsonObject();
        }
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
        if (file.exists()) {
            return;
        }
        try{
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

    public void write(String str, String fileName){
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(this.dirPath + File.separator + fileName));
            out.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                out.close();
            }
        }
    }

    public String read(String fileName){

        File file = new File(dirPath, fileName);

        if (!file.exists()){
            return "";
        }

        String str = "";

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            scanner.useDelimiter("/");
            str = scanner.next();
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + fileName + "\" not found");
        } catch (NoSuchElementException e){
            System.out.println("File \"" + fileName + "\" do not have a line");
        } finally {
            if (scanner != null){
                scanner.close();
            }
        }

        return str;
    }

    /** @return - null, if file is empty.
     * JsonArray, if it exist in the file */
    public JsonArray readJson1(String fileName){
        String str = read(fileName);
        if (str.equals("")){
            return new JsonArray();
        }
        return new JsonParser().parse(str).getAsJsonArray();
    }

    /** @return - null, if file is empty.
     * JsonObject, if it exist in the file */
    public JsonObject readJson(String fileName){
        JsonElement jsonElement1 = new JsonObject();
        JsonElement jsonElement2 = new JsonArray();
        System.out.println("---------------------------------Meow123");
        System.out.println(jsonElement1);
        System.out.println(jsonElement2);
        System.out.println(jsonElement1.getAsJsonArray());
        System.out.println(jsonElement2.getAsJsonObject());

        String str = read(fileName);
        if (str.equals("")) {
            return new JsonObject();
        }
        return new JsonParser().parse(str).getAsJsonObject();
    }

    /** Writes Object to file as JsonElement */
    public void writeJson(Object obj, String fileName){
        if (!new File(this.dirPath, fileName).exists()){
            return;
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(this.dirPath + File.separator + fileName));
            out.write(gson.toJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                out.close();
            }
        }
    }
}
