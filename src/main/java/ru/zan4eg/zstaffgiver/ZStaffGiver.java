package ru.zan4eg.zstaffgiver;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.zan4eg.zstaffgiver.commands.StaffCommand;
import ru.zan4eg.zstaffgiver.commands.StaffCommandReport;
import ru.zan4eg.zstaffgiver.commands.StaffWork;


public final class ZStaffGiver extends JavaPlugin {
    public static ZStaffGiver instance;

    public ZStaffGiver() {
    }

    public static ZStaffGiver getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "==============================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "         ZStaffGiver");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "    Plugin created by Zan4eg");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "   https://vk.com/zan4egpayne");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "==============================");
        this.getCommand("staff").setExecutor(new StaffCommand());
        this.getCommand("report").setExecutor(new StaffCommandReport());
        this.getCommand("sw").setExecutor(new StaffWork());
        Bukkit.getPluginManager().registerEvents(new StaffWork(), this);

    }


    public void onDisable() {
    }
}
