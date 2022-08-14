package me.blueslime.polarwarps.commands;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.commands.sender.Sender;
import me.blueslime.polarwarps.PolarWarps;

import java.util.ArrayList;
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
    public void execute(Sender sender, String command, String[] args) {

        if (args.length == 0) {
            sender.sendColoredMessage("&aCreated by JustJustin with &lLove&a.");
            return;

        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.reload")) {

                plugin.reload();

                sender.sendColoredMessage("&aPlugin has been reloaded!");
            }
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (sender.isConsoleSender() || sender.hasPermission("polarwarps.list")) {
                sender.sendColoredMessage("&aWarps: (" + plugin.getWarpCommand().getWarps().size() + ")");
                sender.sendColoredMessage("&b" + plugin.getWarpCommand().getWarps().keySet().toString().replace("[", "").replace("]", ""));
            }
        }
    }

}
