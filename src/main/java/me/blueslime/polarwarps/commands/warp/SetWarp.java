package me.blueslime.polarwarps.commands.warp;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.commands.sender.Sender;
import dev.mruniverse.slimelib.commands.sender.player.SlimePlayer;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.polarwarps.PolarWarps;
import me.blueslime.polarwarps.SlimeFile;
import me.blueslime.polarwarps.utils.LocationSerializer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Command(
        description = "Set warp command",
        shortDescription = "Set warp",
        usage = "/setwarp (warp name)"
)
public final class SetWarp implements SlimeCommand {

    private final PolarWarps plugin;

    public SetWarp(PolarWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommand() {
        return "setwarp";
    }

    @Override
    public void execute(Sender sender, String commandLabel, String[] args) {

        ConfigurationHandler messages = plugin.getConfigurationHandler(SlimeFile.MESSAGES);

        if (!sender.isPlayer()) {
            sender.sendColoredMessage(
                    messages.getString("messages.console-error", "&cThis command is only for players")
            );
            return;
        }

        if (args.length == 0) {
            sender.sendColoredMessage(
                    messages.getString("messages.set-warp-usage", "&bUsage: &e/setwarp (warp name)&b.")
            );
            return;
        }

        if (args.length == 1 && sender.hasPermission("polarwarps.warp.set")) {

            Player player = ((SlimePlayer)sender).get();

            if (!plugin.getWarpCommand().getWarps().containsKey(args[0])) {
                plugin.getWarpCommand().getWarps().put(args[0], player.getLocation());

                ConfigurationHandler handler = plugin.getConfigurationHandler(SlimeFile.WARPS);

                handler.set(
                        "warps." + args[0] + ".location",
                        LocationSerializer.toString(player.getLocation())
                );


                handler.set(
                        "warps." + args[0] + ".permission.enabled",
                        false
                );

                handler.set(
                        "warps." + args[0] + ".permission.value",
                        "warps." + args[0]
                );

                handler.set(
                        "warps." + args[0] + ".permission.value-other",
                        "warps." + args[0]
                );

                List<String> welcomeList = new ArrayList<>();

                welcomeList.add("&aWelcome to the warp: " + args[0]);
                welcomeList.add("&eStore.server.net");

                handler.set(
                        "warps." + args[0] + ".welcome-message",
                        welcomeList
                );

                handler.save();

                handler.reload();

                sender.sendColoredMessage(
                        messages.getString("messages.set-warp", "&bWarp added!")
                );
                return;
            }
            sender.sendColoredMessage(
                    messages.getString("messages.warp-set-error", "&cThis warp already exists")
            );
        }
    }

}
