package com.zitata.meowutilities.teleport;

import com.zitata.meowutilities.MeowUtilities;
import com.zitata.meowutilities.util.MessageSender;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Teleporter {
    public static void teleportTo(TeleportDelay teleportDelay) {

        EntityPlayerMP playerSource = teleportDelay.playerSource;
        EntityPlayerMP playerTarget = teleportDelay.getPlayerTarget();

        Cooldown cooldown = MeowUtilities.playerList.get(playerSource.getGameProfile().getName()).getCooldown();

        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        WorldServer sourceDim = server.worldServerForDimension(playerSource.dimension);
        WorldServer targetDim = server.worldServerForDimension(teleportDelay.getDimension());

        if (playerSource.dimension != teleportDelay.getDimension()) {
            server.getConfigurationManager().transferPlayerToDimension(
                    playerSource,
                    teleportDelay.getDimension(),
                    new CustomTeleporter(targetDim, teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), teleportDelay.getRotationYawHead(), teleportDelay.getRotationPitch())
            );
        }

        playerSource.fallDistance = 0;
        playerSource.motionX = 0;
        playerSource.motionY = 0;
        playerSource.motionZ = 0;
        playerSource.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(playerSource.experience, playerSource.experienceTotal, playerSource.experienceLevel));
        playerSource.sendPlayerAbilities();

        if (sourceDim.provider.dimensionId == 1 && playerSource.isEntityAlive()) {
            targetDim.spawnEntityInWorld(playerSource);
            targetDim.updateEntityWithOptionalForce(playerSource, false);
        }

        if (teleportDelay.targetType.equals(TeleportDelay.Target.PLAYER)) {
            playerSource.playerNetServerHandler.setPlayerLocation(playerTarget.posX, playerTarget.posY, playerTarget.posZ, playerTarget.rotationYawHead, playerTarget.rotationPitch);
        } else {
            playerSource.playerNetServerHandler.setPlayerLocation(teleportDelay.getX(), teleportDelay.getY(), teleportDelay.getZ(), teleportDelay.getRotationYawHead(), teleportDelay.getRotationPitch());
        }

        switch (teleportDelay.targetType) {
            case TELEPORT_POINT: {
                cooldown.setTp();
                MessageSender.sendMessage(playerSource, MessageSender.SUCCESSFUL, "You has been teleported to " + teleportDelay.getTeleportPoint().getName());
                break;
            }
            case PLAYER: {
                cooldown.setTpa();
                MessageSender.sendMessage(playerSource, MessageSender.SUCCESSFUL, "You have been teleported to " + playerTarget.getGameProfile().getName());
                MessageSender.sendMessage(playerTarget, MessageSender.SUCCESSFUL, playerSource.getGameProfile().getName() + " was teleported to you");
                while (MeowUtilities.tpaRequestList.values().remove(playerSource));
                break;
            }
            case OTHER: {
                cooldown.setBack(); // TODO Сделать телепортацию на последнюю точку смерти
                cooldown.setSpawn(); // TODO Сделать серверные точки телепортации или варпы
                break;
            }
            case TELEPORT_PUBLIC_POINT: {
                cooldown.setTpPublic();
                MessageSender.sendMessage(playerSource, MessageSender.SUCCESSFUL, "You has been teleported to " + teleportDelay.getTeleportPoint().getName());
                break;
            }
        }
        MeowUtilities.teleportDelayList.remove(teleportDelay.playerSource);
    }
}
