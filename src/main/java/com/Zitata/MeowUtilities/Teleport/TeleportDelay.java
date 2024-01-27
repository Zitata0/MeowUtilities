package com.Zitata.MeowUtilities.Teleport;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Point;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class TeleportDelay extends Point {

    private EntityPlayerMP playerSource, playerTarget;
    private long timeStamp;
    private TeleportPoint teleportPoint;

    public TeleportDelay(EntityPlayerMP playerSource, int dimension, double x, double y, double z){
        super(dimension, x, y, z);
        this.playerSource = playerSource;
        teleportDelay();
    }

    public TeleportDelay(EntityPlayerMP playerSource, EntityPlayerMP playerTarget){
        super(playerTarget.dimension, playerTarget.posX, playerTarget.posY, playerTarget.posZ);
        this.playerSource = playerSource;
        this.playerTarget = playerTarget;
        teleportDelay();

        ChatComponentText message = new ChatComponentText(playerSource.getDisplayName() + " will be teleported to you");
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        playerTarget.addChatMessage(message);
    }

    public TeleportDelay(EntityPlayerMP playerSource, TeleportPoint teleportPoint){
        super(teleportPoint.getDimension(), teleportPoint.getX(), teleportPoint.getY(), teleportPoint.getZ());
        this.playerSource = playerSource;
        this.teleportPoint = teleportPoint;
        teleportDelay();
    }

    private void teleportDelay(){
        setTimeStamp();

        ChatComponentText message = new ChatComponentText("You will be teleported in " + MeowUtilities.config.getTeleportDelay() / 1000 + " seconds");
        message.getChatStyle().setColor(MeowUtilities.PASSIVE);
        playerSource.addChatMessage(message);

        int removeIndex = -1;

        for (TeleportDelay teleportDelay : MeowUtilities.teleportDelays){
            if (teleportDelay.getPlayerSource() == this.getPlayerSource()){
                removeIndex = MeowUtilities.teleportDelays.indexOf(teleportDelay);
            }
        }
        if (removeIndex > -1){
            MeowUtilities.teleportDelays.remove(removeIndex);
        }
    }

    public boolean isDelay(){
        if (System.currentTimeMillis() >= timeStamp){
            return true;
        }
        return false;
    }

    public TeleportPoint getTeleportPoint(){
        return teleportPoint;
    }

    public EntityPlayerMP getPlayerSource(){
        return playerSource;
    }

    public EntityPlayerMP getPlayerTarget(){
        return playerTarget;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public void setTimeStamp(){
        this.timeStamp = System.currentTimeMillis() + (MeowUtilities.config.getTeleportDelay());
    }

    public void setPlayerTarget(EntityPlayerMP playerTarget){
        this.playerTarget = playerTarget;
    }

    public void setPlayerSource(EntityPlayerMP playerSource){
        this.playerSource = playerSource;
    }

    public void setTeleportPoint(TeleportPoint teleportPoint){
        this.teleportPoint = teleportPoint;
    }
}
