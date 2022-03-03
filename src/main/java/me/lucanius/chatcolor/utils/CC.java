package me.lucanius.chatcolor.utils;

import org.bukkit.ChatColor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Lucanius
 * March 03, 2022
 */
public class CC {

    public static Map<ChatColor, String> colorValues = new HashMap<>();

    static {
        colorValues.put(ChatColor.BLACK, "BLACK");
        colorValues.put(ChatColor.DARK_BLUE, "DARK_BLUE");
        colorValues.put(ChatColor.DARK_GREEN, "DARK_GREEN");
        colorValues.put(ChatColor.DARK_AQUA, "DARK_AQUA");
        colorValues.put(ChatColor.DARK_RED, "DARK_RED");
        colorValues.put(ChatColor.DARK_PURPLE, "DARK_PURPLE");
        colorValues.put(ChatColor.GOLD, "GOLD");
        colorValues.put(ChatColor.GRAY, "GRAY");
        colorValues.put(ChatColor.DARK_GRAY, "DARK_GRAY");
        colorValues.put(ChatColor.BLUE, "BLUE");
        colorValues.put(ChatColor.GREEN, "GREEN");
        colorValues.put(ChatColor.AQUA, "AQUA");
        colorValues.put(ChatColor.RED, "RED");
        colorValues.put(ChatColor.LIGHT_PURPLE, "LIGHT_PURPLE");
        colorValues.put(ChatColor.YELLOW, "YELLOW");
        colorValues.put(ChatColor.WHITE, "WHITE");
        colorValues.put(ChatColor.RESET, "RESET");
        colorValues.put(ChatColor.ITALIC, "ITALIC");
        colorValues.put(ChatColor.UNDERLINE, "UNDERLINE");
        colorValues.put(ChatColor.STRIKETHROUGH, "STRIKETHROUGH");
        colorValues.put(ChatColor.MAGIC, "MAGIC");
        colorValues.put(ChatColor.BOLD, "BOLD");
    }

    public static final ArrayList<ChatColor> woolColors = new ArrayList<>(
            Arrays.asList(
                    ChatColor.WHITE,
                    ChatColor.GOLD,
                    ChatColor.LIGHT_PURPLE,
                    ChatColor.AQUA,
                    ChatColor.YELLOW,
                    ChatColor.GREEN,
                    ChatColor.LIGHT_PURPLE,
                    ChatColor.DARK_GRAY,
                    ChatColor.GRAY,
                    ChatColor.DARK_AQUA,
                    ChatColor.DARK_PURPLE,
                    ChatColor.BLUE,
                    ChatColor.RESET,
                    ChatColor.DARK_GREEN,
                    ChatColor.RED,
                    ChatColor.BLACK
            )
    );

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> lines) {
        return lines.stream().map(CC::translate).collect(Collectors.toList());
    }

    public static List<String> translate(String[] lines) {
        return translate(Arrays.stream(lines).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    public static String getChatColor(ChatColor color) {
        return colorValues.get(color);
    }

    public static String getSexyChatColor(ChatColor color, boolean b) {
        if (!b) {
            return getChatColor(color);
        }

        String name = getChatColor(color).toLowerCase();
        if (!name.contains("_")) {
            return convertFirstUpperCase(name);
        }

        StringBuilder builder = new StringBuilder();
        String[] split = name.split("_");
        for (String attribute : split) {
            builder.append(convertFirstUpperCase(attribute));
            builder.append(" ");
        }

        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static int getWoolColorFromChatColor(ChatColor color) {
        if (color == ChatColor.DARK_RED) {
            color = ChatColor.RED;
        }
        if (color == ChatColor.DARK_BLUE) {
            color = ChatColor.BLUE;
        }

        return woolColors.indexOf(color);
    }

    public static String convertFirstUpperCase(String source) {
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }
}
