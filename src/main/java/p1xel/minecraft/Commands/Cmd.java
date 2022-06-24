package p1xel.minecraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import p1xel.minecraft.EasyWarp;
import p1xel.minecraft.Storage.Config;
import p1xel.minecraft.Storage.Locale;

import javax.annotation.ParametersAreNonnullByDefault;

public class Cmd implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {

            for (String message : Locale.get().getStringList("commands.main")) {
                sender.sendMessage(Locale.translate(message).replaceAll("%version%", Config.getVersion()));
            }
            return true;

        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {

                if (!sender.hasPermission("easywarp.help")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                if (sender.hasPermission("easywarp.warp")) {
                    sender.sendMessage(Locale.getMessage("commands.warp"));
                }
                if (sender.hasPermission("easywarp.setwarp")) {
                    sender.sendMessage(Locale.getMessage("commands.setwarp"));
                }
                if (sender.hasPermission("easywarp.delwarp")) {
                    sender.sendMessage(Locale.getMessage("commands.delwarp"));
                }
                if (sender.hasPermission("easywarp.reload")) {
                    sender.sendMessage(Locale.getMessage("commands.reload"));
                }
                return true;

            }

            if (args[0].equalsIgnoreCase("reload")) {

                if (!sender.hasPermission("easywarp.reload")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                EasyWarp.getInstance().reloadConfig();
                sender.sendMessage(Locale.getMessage("reload-success"));
                return true;

            }
        }








        return false;
    }

}
