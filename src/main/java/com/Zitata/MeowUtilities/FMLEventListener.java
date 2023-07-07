package com.Zitata.MeowUtilities;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.List;

public class FMLEventListener {

    private List<TeleportDelay> delays;
    private int delaySec;

    public FMLEventListener(){
        delays = MeowUtilities.teleportDelays;
        delaySec = 0;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(LivingHurtEvent event){
        System.out.println("meow " + event.source.getDamageType());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent event){

        //Проверка списка задержанного телепортирования
        if (!this.delays.isEmpty()){
            int removeIndex = -1;

            for (TeleportDelay teleportDelay : this.delays){
                if (teleportDelay.getSender().equals(event.player)){
                    removeIndex = this.delays.indexOf(teleportDelay);
                }
            }

            if (removeIndex > -1){
                this.delays.remove(removeIndex);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onServerTick(TickEvent.ServerTickEvent event){

        //Ограничение на обработку 1 сек
        this.delaySec++;
        if (!(this.delaySec >= 40)){
            return;
        }
        this.delaySec = 0;

        int delay = MeowUtilities.config.getTeleportDelay() * 1000; //Задержка телепортации 5 сек

        if (!this.delays.isEmpty()){
            List<Integer> removeIndex = new ArrayList<Integer>();

            for (TeleportDelay teleportDelay : this.delays){
                if ((System.currentTimeMillis() - teleportDelay.getTimeStamp()) > delay){

                    if (teleportDelay.getTarget() != null){
                        MeowUtilities.teleport.teleportToPlayer(teleportDelay.getSender(), teleportDelay.getTarget());
                    }else {
                        MeowUtilities.teleport.teleportToHome(teleportDelay.getSender(), teleportDelay.getHome(), teleportDelay.getDimension(), teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ());
                    }

                    removeIndex.add(this.delays.indexOf(teleportDelay));
                }
            }

            for (int i : removeIndex){
                this.delays.remove(i);
            }
        }
    }
}
