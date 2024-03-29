package com.Zitata.MeowUtilities.Teleport;

import com.Zitata.MeowUtilities.MeowUtilities;
import net.minecraft.entity.player.EntityPlayerMP;

public class TpaRequest {

    private EntityPlayerMP playerSource;
    private EntityPlayerMP playerTarget;

    public TpaRequest(EntityPlayerMP sender, EntityPlayerMP target){
        this.playerSource = sender;
        this.playerTarget = target;

        int removeIndex = -1;

        for (TpaRequest tpaRequest : MeowUtilities.tpaRequests){
            if (tpaRequest.getPlayerTarget() == this.getPlayerTarget()){
                removeIndex = MeowUtilities.teleportDelays.indexOf(tpaRequest);
            }
        }
        if (removeIndex > -1){
            MeowUtilities.tpaRequests.remove(removeIndex);
        }
    }

    public EntityPlayerMP getPlayerSource(){
        return playerSource;
    }

    public EntityPlayerMP getPlayerTarget(){
        return playerTarget;
    }

    public void setPlayerSource(EntityPlayerMP playerSource) {
        this.playerSource = playerSource;
    }

    public void setPlayerTarget(EntityPlayerMP playerTarget) {
        this.playerTarget = playerTarget;
    }
}
