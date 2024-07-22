package com.zitata.meowutilities.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MessageSender {
    public static void sendMessage(EntityPlayerMP player, EnumChatFormatting color, String text) {
        ChatComponentText message = new ChatComponentText(text);
        message.getChatStyle().setColor(color);
        player.addChatMessage(message);
    }
}
