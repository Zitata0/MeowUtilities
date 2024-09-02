package com.zitata.meowutilities.events;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.data.Data;
import com.zitata.meowutilities.entity.PlayerGhost;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PlayerConnectEvent {
    @SubscribeEvent
    public void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        if (!MeowUtilities.playerList.containsKey(event.player.getDisplayName())) {
            PlayerGhost playerGhost = Data.getPlayerGhost(event.player.getDisplayName());
            if (playerGhost == null) {
                playerGhost = new PlayerGhost();
            }
            MeowUtilities.playerList.put(event.player.getDisplayName(), playerGhost);
        }
    }
}
