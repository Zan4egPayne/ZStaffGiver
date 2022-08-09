package ru.zan4eg.zstaffgiver.commands;

import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.EnderChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.zan4eg.zstaffgiver.ZStaffGiver;

import static ru.zan4eg.zstaffgiver.Colorize.color;

public class StaffWork implements Listener, CommandExecutor {
    FileConfiguration cfg = ZStaffGiver.getInstance().getConfig();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(color(cfg.getString("message.no_cmd")));
            return true;
        }

        if (args[0].equalsIgnoreCase("check")) {
            Player player = (Player) sender;
            if (!player.hasPermission("zstaff.sw.check")) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.no_permission_check")));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(color(cfg.getString("message.no_cmd")));
                return true;
            }

            if (Bukkit.getServer().getPlayer(args[1]) == null) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.no_player")));
                return true;
            }
            Player opponent = player.getServer().getPlayer(args[1]);
            if (isOpponentInGroup(opponent, args[2])) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.group_true").replace("{group}", args[2])));
            } else {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.group_false").replace("{group}", args[2])));
            }
        }

        if (args[0].equalsIgnoreCase("start")) {
            Player player = (Player) sender;
            if (!player.hasPermission("zstaff.sw.use")) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.no_permission_staffwork")));
                return true;
            }
            if (player.isOp()) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.check_op")));
                return true;
            }
            if (player.hasPermission("group.offhelper")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent clear");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent set helper");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.start_msg")));
                return true;
            }
            if (player.hasPermission("group.offsthelper")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent clear");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent set sthelper");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.start_msg")));
                return true;
            }
            if (player.hasPermission("group.offmoder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent clear");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent set moder");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.start_msg")));
                return true;
            }
            if (player.hasPermission("group.offstmoder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent clear");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"lp user " + sender.getName() + " parent set stmoder");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.start_msg")));
                return true;
            }
            sender.sendMessage(color(cfg.getString("StaffWork.messages.no_start")));
            return true;

        }

        if (args[0].equalsIgnoreCase("stop")) {
            Player player = (Player) sender;
            if (!player.hasPermission("zstaff.sw.use")) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.no_permission_staffwork")));
                return true;
            }
            if (player.isOp()) {
                sender.sendMessage(color(cfg.getString("StaffWork.messages.check_op")));
                return true;
            }
            if (player.hasPermission("group.helper")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offhelper".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.stop_msg")));
                return true;
            }

            if (player.hasPermission("group.sthelper")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offsthelper".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.stop_msg")));
                return true;
            }

            if (player.hasPermission("group.mlmoder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offmlmoder".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.stop_msg")));
                return true;
            }

            if (player.hasPermission("group.moder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offmoder".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.stop_msg")));
                return true;
            }

            if (player.hasPermission("group.stmoder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offstmoder".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", player.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", player.getName()));
                sender.sendMessage(color("&f&l=========================================="));
                sender.sendMessage(color(cfg.getString("StaffWork.messages.stop_msg")));
                return true;
            }
            sender.sendMessage(color(cfg.getString("StaffWork.messages.no_stop")));
            return true;

        }
        return true;
    }

    public static boolean isOpponentInGroup(Player opponent, String group) {
        return opponent.hasPermission("group." + group);
    }
    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    @EventHandler
    public void LiveEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (this.cfg.getBoolean("events.PlayerQuitEvent")) {
            if (p.hasPermission("group.admin")) {
                return;
            }
            if (p.hasPermission("group.helper")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offhelper".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", p.getName()));
            }

            if (p.hasPermission("group.sthelper")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offsthelper".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", p.getName()));
            }

            if (p.hasPermission("group.mlmoder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offmlmoder".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", p.getName()));
            }

            if (p.hasPermission("group.moder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offmoder".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", p.getName()));
            }
            if (p.hasPermission("group.stmoder")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent clear".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user %player% parent set offstmoder".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "god %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fly %player%".replace("%player%", p.getName()));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawn %player%".replace("%player%", p.getName()));
            }
        }

    }

    @EventHandler
    public void Attaks(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (((Player)e.getDamager()).getPlayer().hasPermission("group.admin")) {
                e.setCancelled(false);
            }
            if (((Player)e.getDamager()).getPlayer().hasPermission("group.helper") || ((Player)e.getDamager()).getPlayer().hasPermission("group.sthelper") || ((Player)e.getDamager()).getPlayer().hasPermission("group.mlmoder") || ((Player)e.getDamager()).getPlayer().hasPermission("group.moder")  || ((Player)e.getDamager()).getPlayer().hasPermission("group.stmoder")) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void Podbor(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("group.admin")) {
            e.setCancelled((false));
        }

        if (p.hasPermission("group.helper") || p.hasPermission("group.sthelper") || p.hasPermission("group.mlmoder") || p.hasPermission("group.moder")  || p.hasPermission("group.stmoder")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (p.hasPermission("group.admin")) {
            e.setCancelled((false));
        }
        if ((p.hasPermission("group.helper") || p.hasPermission("group.sthelper") || p.hasPermission("group.mlmoder") || p.hasPermission("group.moder")  || p.hasPermission("group.stmoder")) && (e.getInventory().getHolder() instanceof DoubleChest || e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof EnderChest)) {
            e.setCancelled(true);
        }

    }
}
