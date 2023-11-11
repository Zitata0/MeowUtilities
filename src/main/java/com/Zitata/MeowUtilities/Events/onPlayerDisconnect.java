package com.Zitata.MeowUtilities.Events;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.TeleportDelay;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class onPlayerDisconnect {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event){

        if (!MeowUtilities.teleportDelays.isEmpty()){
            int removeIndex = -1;

            for (TeleportDelay teleportDelay : MeowUtilities.teleportDelays){
                if (teleportDelay.getPlayerSource().equals(event.player)){
                    removeIndex = MeowUtilities.teleportDelays.indexOf(teleportDelay);
                }
            }

            if (removeIndex > -1){
                MeowUtilities.teleportDelays.remove(removeIndex);
            }
        }
    }
}
