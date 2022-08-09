package ru.zan4eg.zstaffgiver.Utils;

import com.google.common.collect.TreeBasedTable;

public class CooldownManager {
    public static TreeBasedTable<String, String, Cooldown> accs = TreeBasedTable.create();

    public CooldownManager() {
    }

    public static void createCooldown(String playerName, String name, long time) {
        Cooldown c = new Cooldown(playerName.toLowerCase(), name, time);
        accs.put(playerName.toLowerCase(), name, c);
    }

    public static boolean hasCdw(String playerName, String name) {
        Cooldown c = (Cooldown)accs.get(playerName.toLowerCase(), name);
        if (c == null) {
            return false;
        } else if (c.isLeft()) {
            accs.remove(playerName.toLowerCase(), name);
            return false;
        } else {
            return true;
        }
    }

    public static long getLeftTime(String playerName, String name) {
        return ((Cooldown)accs.get(playerName.toLowerCase(), name)).getLeftTime();
    }
}

