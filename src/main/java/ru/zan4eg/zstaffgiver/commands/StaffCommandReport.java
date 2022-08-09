package ru.zan4eg.zstaffgiver.commands;


import com.ubivashka.vk.spigot.VKAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import ru.zan4eg.zstaffgiver.ZStaffGiver;
import ru.zan4eg.zstaffgiver.Utils.CooldownManager;
import ru.zan4eg.zstaffgiver.Utils.TimeUtils;

import static ru.zan4eg.zstaffgiver.Colorize.color;

public class StaffCommandReport implements CommandExecutor {


    public StaffCommandReport() {
    }

    public String getMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', ZStaffGiver.getInstance().getConfig().getString("Reports.messages." + message));
    }

    public boolean onCommand(CommandSender sender, Command cmd,  String arg, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        } else {
            Player p = ((Player)sender).getPlayer();
            if (cmd.getName().equalsIgnoreCase("report")) {
                if (CooldownManager.hasCdw(sender.getName(), "report")) {
                    sender.sendMessage(this.getMessage("cooldown_report_command").replace("{time}", TimeUtils.getTime(CooldownManager.getLeftTime(sender.getName(), "report"))));
                    return false;
                }

                if (args.length < 2) {
                    p.sendMessage(this.getMessage("noargs"));
                    return false;
                }

                if (args[0].length() < 4) {
                    p.sendMessage(this.getMessage("minSimNick"));
                    return false;
                }

                if (args[0].length() > 16) {
                    p.sendMessage(this.getMessage("maxSimNick"));
                    return false;
                }

                PermissibleBase pb = new PermissibleBase(Bukkit.getOfflinePlayer(args[0]));
                if (pb.hasPermission("zstaff.report.imune")) {
                    sender.sendMessage(this.getMessage("imunnitet"));
                    return false;
                }

                if (sender.getName().equalsIgnoreCase(args[0].toLowerCase())) {
                    sender.sendMessage(this.getMessage("sender_instanceof_player"));
                    return false;
                }
                if (args[0].startsWith("@")) {
                    sender.sendMessage(this.getMessage("all_ping"));
                    return false;
                }

                StringBuffer sb = new StringBuffer();

                for(int i = 1; i < args.length; ++i) {
                    sb.append(args[i]).append(" ");
                }

                if (sb.substring(0, sb.length() - 1).length() < 3) {
                    sender.sendMessage(this.getMessage("minReason"));
                    return false;
                }


                VKAPI.getInstance().getVKUtil().sendMSGtoPeer(ZStaffGiver.getInstance().getConfig().getInt("Reports.peerID"), ("@online #ticket\n⚠ На игрока поступила жалоба! ⚠\n\n\ud83d\udc64 Отправитель: %player%\n❗ Нарушитель: %target%\n\ud83d\udcac Причина жалобы: %reason%\n\n\ud83d\udcd7 Сервер: " + ZStaffGiver.getInstance().getConfig().getString("Reports.server") + "\n\n🎈Репорты при поддержке @zan4egpayne(Николай) и @fleyer001(Максим) ").replace("%player%", sender.getName()).replace("%target%", args[0]).replace("%reason%", sb.substring(0, sb.length() - 1)));
                sender.sendMessage(this.getMessage("successful_send_report").replace("%target%", args[0]).replace("{reason}", sb.substring(0, sb.length() - 1)));
                CooldownManager.createCooldown(sender.getName(), "report", TimeUtils.longTime(ZStaffGiver.getInstance().getConfig().getString("Reports.cooldown")));
            }

            return false;
        }
    }
}

