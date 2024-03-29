package com.Zitata.MeowUtilities.Teleport;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class Teleport {

    public void teleportTo(TeleportDelay teleportDelay){

        EntityPlayerMP playerSource = teleportDelay.getPlayerSource();
        EntityPlayerMP playerTarget = teleportDelay.getPlayerTarget();
        ChatComponentText message;

        if (playerSource.dimension != teleportDelay.getDimension()){
            playerSource.travelToDimension(teleportDelay.getDimension());
        }

        Cooldown cooldown = null;
        for (Cooldown cooldownSelected : MeowUtilities.cooldowns){
            if (cooldownSelected.getName().equals(playerSource.getDisplayName())){
                cooldown = cooldownSelected;
                break;
            }
        }

        if (cooldown == null){
            System.out.println("Error: Cooldown");
            return;
        }

        //Teleport to player
        if (playerTarget != null){
            playerSource.playerNetServerHandler.setPlayerLocation(playerTarget.posX, playerTarget.posY, playerTarget.posZ, playerTarget.rotationYawHead, playerTarget.rotationPitch);
            cooldown.setTpa();

            message = new ChatComponentText("You have been teleported to " + playerTarget.getDisplayName());
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            playerSource.addChatMessage(message);

            message = new ChatComponentText(playerSource.getDisplayName() + " was teleported to you");
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            playerTarget.addChatMessage(message);

        //Teleport to teleportPoint
        }else if (teleportDelay.getTeleportPoint() != null){
            playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), playerSource.rotationYawHead, playerSource.rotationPitch);
            cooldown.setTp();

            message = new ChatComponentText("You has been teleported to " + teleportDelay.getTeleportPoint().getName());
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            playerSource.addChatMessage(message);

        //Teleport to point
        }else{
            playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), playerSource.rotationYawHead, playerSource.rotationPitch);
            cooldown.setBack();
        }
    }
}
