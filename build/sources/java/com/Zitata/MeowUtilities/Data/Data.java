package com.Zitata.MeowUtilities.Data;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.google.gson.Gson;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Data {

    private Gson gson;
    private final String dirPath;
    public final String configFileName;
    public final String cooldownFileName;

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

        this.configFileName = "Config.json";
        this.cooldownFileName = "Cooldown.json";

        createFile(configFileName);
        createFile(cooldownFileName);
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
