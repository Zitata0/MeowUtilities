package com.Zitata.MeowUtilities;

import com.google.gson.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import java.io.*;
import java.util.*;

public class DataStorage {

    private String path;
    public int cooldown;

    public DataStorage(){
        this.path = "." + File.separator;
        this.cooldown = 0;

        //Создание основной папки
        File file = new File(this.path, "MeowUtilities");
        if (!file.exists()){
            file.mkdir();
        }
        this.path += "MeowUtilities";

        //Создание файла с точками дома
        file = new File(this.path, "homes.json");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("File \"homes.json\" was not created");
                e.printStackTrace();
            }
        }

        //Создание файла с временными точками
        file = new File(this.path, "cooldown.json");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("File \"cooldown.json\" was not created");
                e.printStackTrace();
            }
        }

        //Создание файла с конфигурацией
        file = new File(this.path, "config.json");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("File \"config.json\" was not created");
                e.printStackTrace();
            }
        }
    }

    public String toJsonTree(JsonObject jsonObject){
        return jsonObject.toString().replaceAll("\\{", "{\n").replaceAll("}", "\n}").replaceAll(",", ",\n");
    }

    public void addHome(EntityPlayerMP entityPlayerMP, String home, int dimension, double x, double y, double z) {

        String name = entityPlayerMP.getCommandSenderName();
        JsonObject jsonObjectPrimary = getHomesJson();
        if (jsonObjectPrimary == null){
            return;
        }

        boolean hasHome = false;
        if (jsonObjectPrimary.has(name)){
            //Name is true
            JsonArray jsonArray = jsonObjectPrimary.get(name).getAsJsonArray();

            if (jsonArray.size() > 0){
                //Home count is not 0

                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.get("Home").getAsString().equals(home)){
                        //Обновляем данные о старой точке дома
                        jsonObject.addProperty("Home", home);
                        jsonObject.addProperty("Dimension", dimension);
                        jsonObject.addProperty("X", x);
                        jsonObject.addProperty("Y", y);
                        jsonObject.addProperty("Z", z);
                        hasHome = true;

                        break;
                    }
                }
                if (!hasHome){
                    //Do not have home with this name

                    //Ограничение по количеству точек дома
                    if (jsonArray.size() >= MeowUtilities.config.getHomeCount()){
                        ChatComponentText message = new ChatComponentText("You have a lot of homes");
                        message.getChatStyle().setColor(MeowUtilities.ERROR);
                        entityPlayerMP.addChatMessage(message);
                        return;
                    }

                    //Вводим данные о новой точке дома
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("Home", home);
                    jsonObject.addProperty("Dimension", dimension);
                    jsonObject.addProperty("X", x);
                    jsonObject.addProperty("Y", y);
                    jsonObject.addProperty("Z", z);

                    jsonArray.add(jsonObject);
                }
            }else {
                //Home count is 0
                //Вводим данные о новой точке дома
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Home", home);
                jsonObject.addProperty("Dimension", dimension);
                jsonObject.addProperty("X", x);
                jsonObject.addProperty("Y", y);
                jsonObject.addProperty("Z", z);

                jsonArray.add(jsonObject);
            }
        }else {
            //Name is false
            //Вводим данные о новой точке дома
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Home", home);
            jsonObject.addProperty("Dimension", dimension);
            jsonObject.addProperty("X", x);
            jsonObject.addProperty("Y", y);
            jsonObject.addProperty("Z", z);

            JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonObject);

            //Добавляем имя игрока
            jsonObjectPrimary.add(name, jsonArray);
        }

        try{
            PrintWriter out = new PrintWriter(new FileWriter(this.path + File.separator + "homes.json"));
            out.write(toJsonTree(jsonObjectPrimary));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Точка дома добавлена
        if (hasHome){
            ChatComponentText message = new ChatComponentText("The \"" + home + "\" has been modified");
            message.getChatStyle().setColor(MeowUtilities.PASSIVE);
            entityPlayerMP.addChatMessage(message);
        }else{
            ChatComponentText message = new ChatComponentText("The \"" + home + "\" has been added");
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            entityPlayerMP.addChatMessage(message);
        }
    }

    public void delHome(EntityPlayerMP entityPlayerMP, String home){

        String name = entityPlayerMP.getDisplayName();
        JsonObject jsonObjectPrimary = getHomesJson();
        if (jsonObjectPrimary == null){
            return;
        }

        if (jsonObjectPrimary.has(name)){
            //Name is true
            JsonArray jsonArray = jsonObjectPrimary.get(name).getAsJsonArray();
            if (jsonArray.size() > 0){
                //Home count is not 0
                boolean hasHome = false;
                Iterator<JsonElement> iterator = jsonArray.iterator();
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    iterator.next();
                    if (jsonObject.get("Home").getAsString().equals(home)){
                        //Точка дома найдена
                        iterator.remove();
                        hasHome = true;
                        break;
                    }
                }
                if (!hasHome){
                    //Do not have home with this name
                    ChatComponentText message = new ChatComponentText("You do not have home with this name");
                    message.getChatStyle().setColor(MeowUtilities.ERROR);
                    entityPlayerMP.addChatMessage(message);
                    return;
                }
            }else {
                //Home count is 0
                ChatComponentText message = new ChatComponentText("You are tramp");
                message.getChatStyle().setColor(MeowUtilities.ERROR);
                entityPlayerMP.addChatMessage(message);
                return;
            }
        }else {
            //Name is false
            ChatComponentText message = new ChatComponentText("You are tramp");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        try{
            PrintWriter out = new PrintWriter(new FileWriter(this.path + File.separator + "homes.json"));
            out.write(toJsonTree(jsonObjectPrimary));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Точка дома удалена
        ChatComponentText message = new ChatComponentText("The \"" + home + "\" has been removed");
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMP.addChatMessage(message);
    }

    public List<String> getHomeList(EntityPlayerMP entityPlayerMP){

        String name = entityPlayerMP.getDisplayName();
        JsonObject jsonObject = getHomesJson();
        if (jsonObject == null){
            return null;
        }

        List<String> homeList = new ArrayList<String>();

        if (jsonObject.has(name)){
            //Name is true
            JsonArray jsonArray = jsonObject.get(name).getAsJsonArray();

            if (jsonArray.size() > 0){
                //Home count is not 0
                for (JsonElement jsonElement: jsonArray) {
                    homeList.add(jsonElement.getAsJsonObject().get("Home").getAsString());
                }

            }else{
                //Home count is 0
                return homeList;
            }
        }

        //Name is false
        return homeList;
    }

    public Config getConfig(){

        JsonObject jsonObject = getConfigJson();
        if (jsonObject == null){
            return null;
        }

        Config config;
        boolean record = false;

        //Default values
        int teleportDelay = 5;
        int homeCooldown = 300;
        int tpaCooldown = 600;
        int suicideCooldown = 60;
        int homeCount = 1;

        if (jsonObject.toString().equals("")){
            jsonObject.addProperty("teleportDelay", teleportDelay);
            jsonObject.addProperty("homeCooldown", homeCooldown);
            jsonObject.addProperty("tpaCooldown", tpaCooldown);
            jsonObject.addProperty("suicideCooldown", suicideCooldown);
            jsonObject.addProperty("homeCount", homeCount);

            record = true;
            config = new Config(teleportDelay, homeCooldown, tpaCooldown, suicideCooldown, homeCount);
        }else{

            if (jsonObject.has("teleportDelay")){
                teleportDelay = jsonObject.get("teleportDelay").getAsInt();
            }else{
                jsonObject.addProperty("teleportDelay", teleportDelay);
                record = true;
            }

            if (jsonObject.has("homeCooldown")){
                homeCooldown = jsonObject.get("homeCooldown").getAsInt();
            }else{
                jsonObject.addProperty("homeCooldown", homeCooldown);
                record = true;
            }

            if (jsonObject.has("tpaCooldown")){
                tpaCooldown = jsonObject.get("tpaCooldown").getAsInt();
            }else{
                jsonObject.addProperty("tpaCooldown", tpaCooldown);
                record = true;
            }

            if (jsonObject.has("suicideCooldown")){
                suicideCooldown = jsonObject.get("suicideCooldown").getAsInt();
            }else{
                jsonObject.addProperty("suicideCooldown", suicideCooldown);
                record = true;
            }

            if (jsonObject.has("homeCount")){
                homeCount = jsonObject.get("homeCount").getAsInt();
            }else{
                jsonObject.addProperty("homeCount", homeCount);
                record = true;
            }

            config = new Config(teleportDelay, homeCooldown, tpaCooldown, suicideCooldown, homeCount);
        }

        if (record){
            try{
                PrintWriter out = new PrintWriter(new FileWriter(this.path + File.separator + "config.json"));
                out.write(toJsonTree(jsonObject));
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    //Чтение файла "homes.json"
    public JsonObject getHomesJson(){
        File file = new File(this.path, "homes.json");
        String tempStr = "";
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("/"); //Заменить разделитель
            tempStr = scanner.next(); //Конкатенация строк до разделителя
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File \"homes.json\" do not find");
            return null;
        } catch (NoSuchElementException e){
            System.out.println("File \"homes.json\" do not have a line");
        }

        JsonObject jsonObjectPrimary;
        if (!tempStr.equals("")){
            jsonObjectPrimary = new JsonParser().parse(tempStr).getAsJsonObject();
        }else{
            jsonObjectPrimary = new JsonObject();
        }

        return jsonObjectPrimary;
    }

    //Чтение файла "config.json"
    public JsonObject getConfigJson(){
        File file = new File(this.path, "config.json");
        String tempStr = "";
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("/"); //Заменить разделитель
            tempStr = scanner.next(); //Конкатенация строк до разделителя
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File \"config.json\" do not find");
            return null;
        } catch (NoSuchElementException e){
            System.out.println("File \"config.json\" do not have a line");
        }

        JsonObject jsonObject;
        if (!tempStr.equals("")){
            jsonObject = new JsonParser().parse(tempStr).getAsJsonObject();
        }else{
            jsonObject = new JsonObject();
        }

        return jsonObject;
    }

    //Чтение файла "cooldown.json"
    public JsonObject getCooldownJson(){
        File file = new File(this.path, "cooldown.json");
        String tempStr = "";
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("/"); //Заменить разделитель
            tempStr = scanner.next(); //Конкатенация строк до разделителя
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File \"cooldown.json\" do not find");
            return null;
        } catch (NoSuchElementException e){
            System.out.println("File \"cooldown.json\" do not have a line");
        }

        JsonObject jsonObject;
        if (!tempStr.equals("")){
            jsonObject = new JsonParser().parse(tempStr).getAsJsonObject();
        }else{
            jsonObject = new JsonObject();
        }

        return jsonObject;
    }

    public boolean isCooldown(EntityPlayerMP entityPlayerMP, String property){

        JsonObject jsonObject = MeowUtilities.dataStorage.getCooldownJson();
        if (jsonObject == null){
            return true;
        }

        //Перезарядка
        JsonObject jsonObjectProperties = new JsonObject();
        jsonObjectProperties.addProperty("home", MeowUtilities.config.getHomeCooldown() * 1000);
        jsonObjectProperties.addProperty("tpa", MeowUtilities.config.getTpaCooldown() * 1000);
        jsonObjectProperties.addProperty("suicide", MeowUtilities.config.getSuicideCooldown() * 1000);

        if (!jsonObjectProperties.has(property)) {
            return true;
        }

        String name = entityPlayerMP.getDisplayName();
        if (jsonObject.has(name)){
            //Name is true
            JsonObject jsonObjectCooldowns = jsonObject.getAsJsonObject(name);

            if (jsonObjectCooldowns.has(property)){
                //Property is true
                long time = System.currentTimeMillis() - jsonObjectCooldowns.get(property).getAsLong();

                if (time > jsonObjectProperties.get(property).getAsLong()){
                    return false;
                }else{
                    this.cooldown = (int)((jsonObjectProperties.get(property).getAsLong() - time) / 1000);
                    return true;
                }
            }else{
                //Property is false
                return false;
            }
        }else {
            //Name is false
            return false;
        }
    }

    public void setCooldown(EntityPlayerMP entityPlayerMP, String property){

        JsonObject jsonObject = MeowUtilities.dataStorage.getCooldownJson();
        if (jsonObject == null){
            return;
        }

        String name = entityPlayerMP.getDisplayName();
        if (jsonObject.has(name)) {
            //Name is true
            JsonObject jsonObjectCooldown = jsonObject.getAsJsonObject(name);
            jsonObjectCooldown.addProperty(property, System.currentTimeMillis());

        }else{
            //Name is false
            JsonObject jsonObjectCooldown = new JsonObject();
            jsonObjectCooldown.addProperty(property, System.currentTimeMillis());
            jsonObject.add(name, jsonObjectCooldown);
        }

        try{
            PrintWriter out = new PrintWriter(new FileWriter(this.path + File.separator + "cooldown.json"));
            out.write(toJsonTree(jsonObject));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
