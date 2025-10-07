package com.zitata.meowutilities.events;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import java.util.AbstractMap;

public class PlayerDisconnectEvent {
    @SubscribeEvent
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
        if (MeowUtilities.playerList.containsKey(event.player.getGameProfile().getId().toString())) {
            Data.savePlayerGhost(new AbstractMap.SimpleEntry<>(event.player.getGameProfile().getId().toString(), MeowUtilities.playerList.get(event.player.getGameProfile().getId().toString())));
            MeowUtilities.playerList.remove(event.player.getGameProfile().getId().toString());
        }
        MeowUtilities.tpaRequestList.remove(event.player);
        while (MeowUtilities.tpaRequestList.values().remove(event.player));
        MeowUtilities.teleportDelayList.remove(event.player);
    }
}
