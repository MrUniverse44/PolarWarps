package me.blueslime.polarwarps.commands.warp;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.source.SlimeSource;
import me.blueslime.polarwarps.SlimeFile;
import me.blueslime.polarwarps.PolarWarps;

@Command(
        description = "Warps command",
        shortDescription = "Warps",
        usage = "/Warps (warp name)"
)
public final class Warps implements SlimeCommand {

    private final PolarWarps plugin;

    public Warps(PolarWarps plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getCommand() {
        return "warps";
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
            sender.sendColoredMessage("&aWarps: (" + plugin.getWarpCommand().getWarps().size() + ")");
            sender.sendColoredMessage("&b" + plugin.getWarpCommand().getWarps().keySet().toString().replace("[", "").replace("]", ""));
            return;
        }

        plugin.getWarpCommand().execute(
                sender,
                commandLabel,
                args
        );
    }
}
