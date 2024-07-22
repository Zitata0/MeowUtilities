package com.zitata.meowutilities.events;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.teleport.TeleportDelay;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ServerTickEvent {

    private long secTimeStamp;
    private long saveDataTimeStamp;

    public ServerTickEvent() {
        secTimeStamp = System.currentTimeMillis();
        setSaveDataTimeStamp();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onServerTick(TickEvent.ServerTickEvent event) {

        //Sec time
        if (System.currentTimeMillis() - 1000 < secTimeStamp) {
            return;
        }

        secTimeStamp = System.currentTimeMillis();

        if (System.currentTimeMillis() >= saveDataTimeStamp) {
            Data.saveAllPlayerGhost(MeowUtilities.playerList);
            Data.saveConfig(MeowUtilities.config);
            setSaveDataTimeStamp();
        }

        for (TeleportDelay teleportDelay : MeowUtilities.teleportDelayList.values()) {
            if (teleportDelay.isDelay()) {
                MeowUtilities.teleporter.teleportTo(teleportDelay);
                break;
            }
        }
    }

    private void setSaveDataTimeStamp() {
        saveDataTimeStamp = System.currentTimeMillis() + MeowUtilities.config.getDataSave(); //Save data every 5 min
    }
}
