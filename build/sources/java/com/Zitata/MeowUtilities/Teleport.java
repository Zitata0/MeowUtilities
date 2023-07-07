package com.Zitata.MeowUtilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class Teleport {

    public Teleport(){

    }

    public void teleportToHome(EntityPlayerMP entityPlayerMP, String home){

        String name = entityPlayerMP.getCommandSenderName();
        float x = 0, y = 0, z = 0;
        int dimension = 0;

        JsonObject jsonObjectPrimary = MeowUtilities.dataStorage.getHomesJson();
        if (jsonObjectPrimary == null){
            return;
        }

        if (jsonObjectPrimary.has(name)){
            //Name is true
            JsonArray jsonArray = jsonObjectPrimary.get(name).getAsJsonArray();
            if (jsonArray.size() > 0){
                //Home count is not 0
                boolean hasHome = false;
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.get("Home").getAsString().equals(home)){
                        //Берем данные о точке дома
                        dimension = jsonObject.get("Dimension").getAsInt();
                        x = jsonObject.get("X").getAsFloat();
                        y = jsonObject.get("Y").getAsFloat();
                        z = jsonObject.get("Z").getAsFloat();
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

        //Проверка перезарядки
        if (MeowUtilities.dataStorage.isCooldown(entityPlayerMP, "home")){
            ChatComponentText message = new ChatComponentText("Home will recharge in " + MeowUtilities.dataStorage.cooldown + " seconds");
            message.getChatStyle().setColor(MeowUtilities.ERROR);
            entityPlayerMP.addChatMessage(message);
            return;
        }

        //Сообщение отправителю
        ChatComponentText message = new ChatComponentText("You will be teleported in 5 seconds");
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        entityPlayerMP.addChatMessage(message);

        boolean hasDelay = false;
        for (TeleportDelay teleportDelay : MeowUtilities.teleportDelays){
            if (teleportDelay.getSender().equals(entityPlayerMP)){
                MeowUtilities.teleportDelays.remove(teleportDelay);
                MeowUtilities.teleportDelays.add(new TeleportDelay(entityPlayerMP, home, dimension, x, y, z)); //Добавление в список телепортации с задержкой
                hasDelay = true;
            }
        }
        if (!hasDelay){
            MeowUtilities.teleportDelays.add(new TeleportDelay(entityPlayerMP, home, dimension, x, y, z)); //Добавление в список телепортации с задержкой
        }
    }

    public void teleportToHome(EntityPlayerMP entityPlayerMP, String home, int dimension, float x, float y, float z){
        if (entityPlayerMP.dimension != dimension){
            entityPlayerMP.travelToDimension(dimension);
        }
        entityPlayerMP.playerNetServerHandler.setPlayerLocation(x, y, z, entityPlayerMP.rotationYawHead, entityPlayerMP.rotationPitch);
        MeowUtilities.dataStorage.setCooldown(entityPlayerMP, "home");

        //Сообщение отправителю
        ChatComponentText message = new ChatComponentText("You have been teleported to " + home);
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMP.addChatMessage(message);
    }

    //Source - Кто тепается //Target - К кому тепается
    public void teleportToPlayer(EntityPlayerMP entityPlayerMPSource, EntityPlayerMP entityPlayerMPTarget){
        if (entityPlayerMPSource.dimension != entityPlayerMPTarget.dimension){
            entityPlayerMPSource.travelToDimension(entityPlayerMPTarget.dimension);
        }

        entityPlayerMPSource.playerNetServerHandler.setPlayerLocation(entityPlayerMPTarget.posX, entityPlayerMPTarget.posY, entityPlayerMPTarget.posZ, entityPlayerMPTarget.rotationYawHead, entityPlayerMPTarget.rotationPitch);
        MeowUtilities.dataStorage.setCooldown(entityPlayerMPSource, "tpa");

        //Сообщение цели
        ChatComponentText message = new ChatComponentText(entityPlayerMPSource.getDisplayName() + " was teleported to you");
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMPTarget.addChatMessage(message);

        //Сообщение отправителю
        message = new ChatComponentText("You have been teleported to " + entityPlayerMPTarget.getDisplayName());
        message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
        entityPlayerMPSource.addChatMessage(message);
    }

}
