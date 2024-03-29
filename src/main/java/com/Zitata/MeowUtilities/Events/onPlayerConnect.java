package com.Zitata.MeowUtilities.Events;

import com.Zitata.MeowUtilities.MeowUtilities;
import com.Zitata.MeowUtilities.Teleport.Patterns.Cooldown;
import com.Zitata.MeowUtilities.Teleport.Patterns.TeleportPoint;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;

public class onPlayerConnect {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event){
        if (!MeowUtilities.teleportPoints.containsKey(event.player.getDisplayName())){
            MeowUtilities.teleportPoints.put(event.player.getDisplayName(), new ArrayList<TeleportPoint>());
        }

        Cooldown cooldown = null;
        for (Cooldown cooldownSelected : MeowUtilities.cooldowns){
            if (cooldownSelected.getName().equals(event.player.getDisplayName())){
                cooldown = cooldownSelected;
                break;
            }
        }

        if (cooldown == null){
            MeowUtilities.cooldowns.add(new Cooldown(event.player.getDisplayName()));
        }
    }
}
