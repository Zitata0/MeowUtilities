package com.Zitata.MeowUtilities;

import net.minecraft.entity.player.EntityPlayerMP;

public class TpaRequest {

    private EntityPlayerMP entityPlayerMPSender;
    private EntityPlayerMP entityPlayerMPTarget;

    public TpaRequest(EntityPlayerMP entityPlayerMPSender, EntityPlayerMP entityPlayerMPTarget){
        this.entityPlayerMPSender = entityPlayerMPSender;
        this.entityPlayerMPTarget = entityPlayerMPTarget;
    }

    public EntityPlayerMP getSender(){
        return this.entityPlayerMPSender;
    }

    public EntityPlayerMP getTarget(){
        return this.entityPlayerMPTarget;
    }
}
