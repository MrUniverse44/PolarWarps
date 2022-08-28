package me.blueslime.polarwarps.commands.warp;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.source.SlimeSource;
import me.blueslime.polarwarps.PolarWarps;
import me.blueslime.polarwarps.SlimeFile;

@Command(
        description = "Delete warp command",
        shortDescription = "Delete warp",
        usage = "/delwarp (warp name)"
)
public final class DeleteWarp implements SlimeCommand {

    private final PolarWarps plugin;

    public DeleteWarp(PolarWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommand() {
        return "delwarp";
    }

    @Override
    public void execute(SlimeSource sender, String commandLabel, String[] args) {

        ConfigurationHandler messages = plugin.getConfigurationHandler(SlimeFile.MESSAGES);

        if (!sender.isPlayer()) {
            sender.sendColoredMessage(
                    messages.getString("messages.console-error", "&cThis command is only for players")
            );
            return;
        }

        if (args.length == 0) {
            sender.sendColoredMessage(
                    messages.getString("messages.del-warp-usage", "&bUsage: &e/delwarp (warp name)&b.")
            );
            return;
        }

        if (args.length == 1 && sender.hasPermission("polarwarps.warp.delete")) {
            if (plugin.getWarpCommand().getWarps().containsKey(args[0])) {
                plugin.getWarpCommand().getWarps().remove(args[0]);

                plugin.getConfigurationHandler(SlimeFile.WARPS).set("warps." + args[0], null);

                plugin.getConfigurationHandler(SlimeFile.WARPS).save();

                plugin.getConfigurationHandler(SlimeFile.WARPS).reload();

                sender.sendColoredMessage(
                        messages.getString("messages.del-warp", "&bWarp removed!")
                );
                return;
            }
            sender.sendColoredMessage(
                    messages.getString("messages.warp-error", "&cThis warp doesn't exists")
            );
        }
    }

}
