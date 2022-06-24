package p1xel.minecraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p1xel.minecraft.Storage.Locale;
import p1xel.minecraft.Storage.Warps;

import javax.annotation.ParametersAreNonnullByDefault;

public class DelWarpCmd implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Locale.getMessage("must-be-player"));
            return true;
        }

        if (!sender.hasPermission("easywarp.delwarp")) {
            sender.sendMessage(Locale.getMessage("no-perm"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Locale.getMessage("commands.delwarp"));
            return true;
        }

        if (args.length == 1) {

            if (!Warps.isWarpExist(args[0])) {
                sender.sendMessage(Locale.getMessage("not-exist").replaceAll("%warp%", args[0]));
                return true;
            }

            Player p = (Player) sender;

            Warps.removeWarp(args[0]);
            sender.sendMessage(Locale.getMessage("delwarp-success").replaceAll("%warp%", args[0]));
            return true;

        }

        return false;
    }

}
