package com.Zitata.MeowUtilities.Events;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.TeleportDelay;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class onServerTick {

    private long secTimeStamp;
    private long saveDataTimeStamp;

    public onServerTick(){
        secTimeStamp = System.currentTimeMillis();
        setSaveDataTimeStamp();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onServerTick(TickEvent.ServerTickEvent event){

        //Sec time
        if (System.currentTimeMillis() - 1000 < secTimeStamp){
            return;
        }

        secTimeStamp = System.currentTimeMillis();

        if (System.currentTimeMillis() >= saveDataTimeStamp){
            MeowUtilities.config.saveConfig();
            MeowUtilities.data.saveCooldown();
            MeowUtilities.data.saveTeleportPoints();
            setSaveDataTimeStamp();
        }

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

    private void setSaveDataTimeStamp(){
        saveDataTimeStamp = System.currentTimeMillis() + MeowUtilities.config.getDataSave(); //Save data every 5 min
    }
}
