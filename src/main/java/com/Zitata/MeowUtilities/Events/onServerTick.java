package com.Zitata.MeowUtilities.Events;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.TeleportDelay;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class onServerTick {

    private int delaySec;

    public onServerTick(){
        delaySec = 0;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onServerTick(TickEvent.ServerTickEvent event){

        this.delaySec++;
        if (this.delaySec < 40){
            return;
        }
        this.delaySec = 0;

        int removeIndex = -1;
        for (TeleportDelay teleportDelay : MeowUtilities.teleportDelays){
            if (teleportDelay.isDelay()){
                MeowUtilities.teleport.teleportTo(teleportDelay);
                removeIndex = MeowUtilities.teleportDelays.indexOf(teleportDelay);
            }
        }
        if (removeIndex > -1){
            MeowUtilities.teleportDelays.remove(removeIndex);
        }
    }
}
