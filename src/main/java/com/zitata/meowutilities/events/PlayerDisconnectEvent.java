package com.zitata.meowutilities.events;

import com.zitata.meowutilities.MeowUtilities;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PlayerDisconnectEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        MeowUtilities.playerList.remove(event.player.getDisplayName());
        MeowUtilities.tpaRequestList.remove(event.player);
        while (MeowUtilities.tpaRequestList.values().remove(event.player)) ;
        MeowUtilities.teleportDelayList.remove(event.player);
    }
}
