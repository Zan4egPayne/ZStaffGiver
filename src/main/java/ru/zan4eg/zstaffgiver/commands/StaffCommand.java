package ru.zan4eg.zstaffgiver.commands;

import com.ubivashka.vk.spigot.VKAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.zan4eg.zstaffgiver.ZStaffGiver;

public class StaffCommand implements CommandExecutor {
    FileConfiguration cfg = ZStaffGiver.getInstance().getConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (cfg.getString("settings.hide_tips").equalsIgnoreCase("true")) {
            if (args.length < 1) {
                sender.sendMessage(color(cfg.getString("message.no_cmd")));
                return true;
            }

            if (args[0].equalsIgnoreCase("give")) {
                if (!player.hasPermission("zstaff.use")) {
                    sender.sendMessage(color(cfg.getString("message.no_permission")));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(color(cfg.getString("message.no_arguments")));
                    return true;
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cfg.getString("cmd_groups_give").replace("{user}", args[1]).replace("{group}", cfg.getString("groups." + args[2] + ".group")));
                sender.sendMessage(color(cfg.getString("message.confirm_give").replace("{user}", args[1])));
                VKAPI.getInstance().getVKUtil().sendMSGtoPeer(ZStaffGiver.getInstance().getConfig().getInt("settings.peerID"), ("===================\n             #given\n🧩Группа: %group%\n💚Игрок: %user%\n\n✅Права успешно выданы").replace("%group%", cfg.getString("groups." + args[2] + ".prefix")).replace("%user%", args[1]));
                return true;
            }

            if (args[0].equalsIgnoreCase("take")) {
                if (!player.hasPermission("zstaff.use")) {
                    sender.sendMessage(color(cfg.getString("message.no_permission")));
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage(color(cfg.getString("message.no_arguments")));
                    return true;
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cfg.getString("cmd_groups_take").replace("{user}", args[1]));
                sender.sendMessage(color(cfg.getString("message.confirm_take").replace("{user}", args[1])));
                VKAPI.getInstance().getVKUtil().sendMSGtoPeer(ZStaffGiver.getInstance().getConfig().getInt("settings.peerID"), ("===================\n             #taken\n❤Игрок: %user%\n\n❌Права успешно сняты").replace("%user%", args[1]));
                return true;
            }
        }
        if (cfg.getString("settings.hide_tips").equalsIgnoreCase("false")) {
            if (args.length < 1) {
                sender.sendMessage(color(cfg.getString("message.no_cmd") + " &8(give/take)"));
                return true;
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (!player.hasPermission("zstaff.use")) {
                    sender.sendMessage(color(cfg.getString("message.no_permission") + " &8(zstaff.use)"));
                    return true;
                }

                if (args.length < 3) {
                    sender.sendMessage(color(cfg.getString("message.no_arguments") + " &8(/staff give <ник> <группа(config.yml)>"));
                    return true;
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cfg.getString("cmd_groups_give").replace("{user}", args[1]).replace("{group}", cfg.getString("groups." + args[2] + ".group")));
                sender.sendMessage(color(cfg.getString("message.confirm_give").replace("{user}", args[1])));
                VKAPI.getInstance().getVKUtil().sendMSGtoPeer(ZStaffGiver.getInstance().getConfig().getInt("settings.peerID"), ("===================\n             #given\n🧩Группа: %group%\n💚Игрок: %user%\n\n✅Права успешно выданы").replace("%group%", cfg.getString("groups." + args[2] + ".prefix")).replace("%user%", args[1]));
                return true;
            }

            if (args[0].equalsIgnoreCase("take")) {
                if (!player.hasPermission("zstaff.use")) {
                    sender.sendMessage(color(cfg.getString("message.no_permission") + " &8(zstaff.use)"));
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage(color(cfg.getString("message.no_arguments") + " &8(/staff take <ник>"));
                    return true;
                }
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cfg.getString("cmd_groups_take").replace("{user}", args[1]));
                sender.sendMessage(color(cfg.getString("message.confirm_take").replace("{user}", args[1])));
                VKAPI.getInstance().getVKUtil().sendMSGtoPeer(ZStaffGiver.getInstance().getConfig().getInt("settings.peerID"), ("===================\n             #taken\n❤Игрок: %user%\n\n❌Права успешно сняты").replace("%user%", args[1]));
                return true;
            }
        }
        return true;
    }

    private String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
