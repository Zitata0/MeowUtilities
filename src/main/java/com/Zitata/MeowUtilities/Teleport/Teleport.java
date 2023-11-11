package com.Zitata.MeowUtilities.Teleport;

import com.Zitata.MeowUtilities.MeowUtilities;
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

        if (playerTarget != null){
            playerSource.playerNetServerHandler.setPlayerLocation(playerTarget.posX, playerTarget.posY, playerTarget.posZ, playerTarget.rotationYawHead, playerTarget.rotationPitch);

            message = new ChatComponentText("You have been teleported to " + playerTarget.getDisplayName());
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            playerSource.addChatMessage(message);

            message = new ChatComponentText(playerSource.getDisplayName() + " was teleported to you");
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            playerTarget.addChatMessage(message);
        }else if (teleportDelay.getTeleportPoint() != null){
            playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), playerSource.rotationYawHead, playerSource.rotationPitch);

            message = new ChatComponentText("You has been teleported to " + teleportDelay.getTeleportPoint().getName());
            message.getChatStyle().setColor(MeowUtilities.SUCCESSFUL);
            playerSource.addChatMessage(message);
        }else{
            playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), playerSource.rotationYawHead, playerSource.rotationPitch);
        }
    }
}
