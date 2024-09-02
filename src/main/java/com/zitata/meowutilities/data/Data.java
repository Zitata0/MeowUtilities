package com.zitata.meowutilities.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.entity.PlayerGhost;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Data {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final File ROOT_DIR = new File("." + File.separator + MeowUtilities.modName);
    private static final File PLAYERS_DIR = new File(ROOT_DIR, "players");
    public static final File CONFIG_FILE = new File(ROOT_DIR, "config.json");

    public Data() {
        createDir(ROOT_DIR);
        createDir(PLAYERS_DIR);
        createFile(CONFIG_FILE);
    }

    public static Config readConfig() {
        Config config;
        JsonElement result = readJson(CONFIG_FILE);
        if (result.isJsonObject()) {
            config = GSON.fromJson(result.getAsJsonObject(), Config.class);
        } else {
            config = new Config();
        }
        saveConfig(config);
        return config;
    }

    public static void saveConfig(Config config) {
        writeJson(CONFIG_FILE, GSON.toJsonTree(config));
    }

    public static PlayerGhost getPlayerGhost(String playerName) {
        File file = new File(PLAYERS_DIR, playerName + ".json");
        if (!file.exists()) {
            return null;
        }
        JsonObject data = readJson(file).getAsJsonObject();
        return GSON.fromJson(data, PlayerGhost.class);
    }

    public static void savePlayerGhost(Map.Entry<String, PlayerGhost> playerGhost) {
        writeJson(new File(PLAYERS_DIR, playerGhost.getKey() + ".json"), GSON.toJsonTree(playerGhost.getValue()));
    }

    public static void saveAllPlayerGhost(Map<String, PlayerGhost> playerGhosts) {
        for (Map.Entry<String, PlayerGhost> playerGhost : playerGhosts.entrySet()) {
            savePlayerGhost(playerGhost);
        }
    }

    public static void createDir(File file) {
        if (!file.exists()) {
            if (!file.mkdir()) {
                System.out.println("Dir \"" + file.getName() + "\" has been created");
            }
        }
    }

    public static void createFile(File file) {
        if (file.exists()) {
            return;
        }
        try {
            if (file.createNewFile()) {
                System.out.println("File \"" + file.getName() + "\" has been created");
            } else {
                System.out.println("File " + file.getName() + " was not created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJson(File file, JsonElement jsonElement) {
        if (!file.exists()) {
            createFile(file);
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            GSON.toJson(jsonElement, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonElement readJson(File file) {
        JsonElement data = null;
        try {
            FileReader fileReader = new FileReader(file);
            data = GSON.fromJson(fileReader, JsonElement.class);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data == null) {
            data = new JsonObject();
        }
        return data;
    }
}
