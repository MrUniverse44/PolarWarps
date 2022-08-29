package me.blueslime.polarwarps.commands;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.source.SlimeSource;
import me.blueslime.polarwarps.PolarWarps;
import org.bukkit.Sound;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(
        description = "Main plugin command",
        shortDescription = "Plugin Command",
        usage = "/pw (args)"
)
public final class PluginCommand implements SlimeCommand {

    private final PolarWarps plugin;

    public PluginCommand(PolarWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommand() {
        return "polarwarps";
    }

     @Override
     public List<String> getAliases() {
        ArrayList<String> list = new ArrayList<>();
        list.add("pw");
        return list;
     }

    @Override
    public void execute(SlimeSource sender, String command, String[] args) {

        if (args.length == 0) {
            sender.sendColoredMessage("&aCreated by JustJustin with &lLove&a.");
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.admin")) {
                sender.sendColoredMessage("&7(Detected Admin permissions, showing admin commands)");
                sender.sendColoredMessage("&7- &a/polarwarps reload");
                sender.sendColoredMessage("&7- &a/polarwarps sounds");
                sender.sendColoredMessage("&7- &a/polarwarps particles");
                sender.sendColoredMessage("&7- &a/polarwarps list");
            }
            return;

        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.reload")) {
                long last = System.currentTimeMillis();
                plugin.reload();
                long now = System.currentTimeMillis();
                sender.sendColoredMessage("&aPlugin has been reloaded in " + (now - last) + " ms!");
                return;
            }
        }

        if (args[0].equalsIgnoreCase("sounds")) {
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.sounds")) {
                sender.sendColoredMessage("&3Sounds: (" + Sound.values().length + ")");
                sender.sendColoredMessage(
                        "&b" + Arrays.toString(Sound.values())
                                .replace("[", "")
                                .replace("]", "")
                );
            }
            return;
        }

        if (args[0].equalsIgnoreCase("particles")) {
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.particles")) {
                sender.sendColoredMessage("&6Particles: (" + Sound.values().length + ")");
                sender.sendColoredMessage(
                        "&e" + Arrays.toString(ParticleEffect.values())
                                .replace("[", "")
                                .replace("]", "")
                );
            }
            return;
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.list")) {
                sender.sendColoredMessage("&5Warps: (" + plugin.getWarpCommand().getWarps().size() + ")");
                sender.sendColoredMessage(
                        "&d" + plugin.getWarpCommand().getWarps().keySet().toString()
                                .replace("[", "")
                                .replace("]", "")
                );
            }
        }
    }

}
