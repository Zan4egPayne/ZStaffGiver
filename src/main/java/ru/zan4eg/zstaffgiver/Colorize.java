package ru.zan4eg.zstaffgiver;

import org.bukkit.ChatColor;

public class Colorize {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String prefix() {
        return color("&d&lSAFETYPLAY &7▸ &f");
    }

}