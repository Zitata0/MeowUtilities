package com.zitata.meowutilities.events;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PlayerConnectEvent {
    @SubscribeEvent
    public void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        if (!MeowUtilities.playerList.containsKey(event.player.getDisplayName())) {
            MeowUtilities.playerList.put(event.player.getDisplayName(), Data.getPlayerGhost(event.player.getDisplayName()));
        }
    }
}
