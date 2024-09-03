package com.zitata.meowutilities.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class MessageSender {
    public static final EnumChatFormatting SUCCESSFUL = EnumChatFormatting.GREEN;
    public static final EnumChatFormatting ERROR = EnumChatFormatting.RED;
    public static final EnumChatFormatting PASSIVE = EnumChatFormatting.GRAY;
    public static final EnumChatFormatting NEUTRAL = EnumChatFormatting.WHITE;

    private static ChatComponentTranslation translate(EnumChatFormatting color, String text, Object ... objects) {
        ChatComponentTranslation message = new ChatComponentTranslation(text, objects);
        message.getChatStyle().setColor(color);
        return message;
    }

    private static ChatComponentText format(EnumChatFormatting color, String text) {
        ChatComponentText message = new ChatComponentText(text);
        message.getChatStyle().setColor(color);
        return message;
    }

    public static void sendMessage(ICommandSender player, EnumChatFormatting color, String text) {
        player.addChatMessage(format(color, text));
    }

    public static void sendTranslatedMessage(ICommandSender player, EnumChatFormatting color, String text, Object ... objects) {
        System.out.println(translate(color, text, objects));
        player.addChatMessage(translate(color, text, objects));
    }

    public static void sendServerMessage(EnumChatFormatting color, String text) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(format(color, text));
    }

    public static void sendTranslatedServerMessage(EnumChatFormatting color, String text, Object ... objects) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(translate(color, text, objects));
    }
}
