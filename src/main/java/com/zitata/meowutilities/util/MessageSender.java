package com.zitata.meowutilities.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MessageSender {
    public static final EnumChatFormatting SUCCESSFUL = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting ERROR = EnumChatFormatting.RED;
    public static final EnumChatFormatting PASSIVE = EnumChatFormatting.GRAY;
    public static final EnumChatFormatting NEUTRAL = EnumChatFormatting.WHITE;

    public static void sendMessage(EntityPlayerMP player, EnumChatFormatting color, String text) {
        ChatComponentText message = new ChatComponentText(text);
        message.getChatStyle().setColor(color);
        player.addChatMessage(message);
    }
}
