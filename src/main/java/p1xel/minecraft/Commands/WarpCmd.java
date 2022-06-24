package p1xel.minecraft.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import p1xel.minecraft.EasyWarp;
import p1xel.minecraft.Storage.Config;
import p1xel.minecraft.Storage.Locale;
import p1xel.minecraft.Storage.Warps;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;

public class WarpCmd implements CommandExecutor {

    HashMap<String, Integer> second = new HashMap<>();

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String senderName = sender.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Locale.getMessage("must-be-player"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Locale.getMessage("commands.warp"));
            return true;
        }

        if (args.length <= 2) {

            if (!Warps.isWarpExist(args[0])) {
                sender.sendMessage(Locale.getMessage("not-exist").replaceAll("%warp%", args[0]));
                return true;
            }

            if (!sender.hasPermission("easywarp.warp")) {

                if (!sender.hasPermission("easywarp.warp." + args[0])) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

            }

            if (second.get(senderName) != null) {
                sender.sendMessage(Locale.getMessage("cd").replaceAll("%second%", String.valueOf(second.get(senderName))));
                return true;
            }

            if (args.length == 2) {

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    sender.sendMessage(Locale.getMessage("invalid-player"));
                    return true;
                }

                sender.sendMessage(Locale.getMessage("warp-teleporting"));
                target.sendMessage(Locale.getMessage("warp-teleporting"));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.sendMessage(Locale.getMessage("warp-success").replaceAll("%warp%", args[0]).replaceAll("%warplabel%", Warps.getWarpLabel(args[0])));
                        target.teleport(Warps.getWarpLoc(args[0]));
                    }
                }.runTaskLater(EasyWarp.getInstance(), 20L * Config.getInt("wait"));

                return true;

            }

            Player p = (Player) sender;

            second.put(senderName, Config.getInt("cd"));

            sender.sendMessage(Locale.getMessage("warp-teleporting"));

            new BukkitRunnable() {
                @Override
                public void run() {
                    sender.sendMessage(Locale.getMessage("warp-success").replaceAll("%warp%", args[0]).replaceAll("%warplabel%", Warps.getWarpLabel(args[0])));
                    p.teleport(Warps.getWarpLoc(args[0]));
                }
            }.runTaskLater(EasyWarp.getInstance(), 20L * Config.getInt("wait"));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (second.get(senderName) <= 0) {
                        second.remove(senderName);
                        cancel();
                    }
                    second.put(senderName, second.get(senderName) - 1);
                }
            }.runTaskTimer(EasyWarp.getInstance(), 0L, 20L);

            return true;

        }









        return false;
    }


}
