package me.blueslime.polarwarps.commands.warp;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.source.SlimeSource;
import dev.mruniverse.slimelib.source.player.SlimePlayer;
import me.blueslime.polarwarps.PolarWarps;
import me.blueslime.polarwarps.SlimeFile;
import me.blueslime.polarwarps.utils.LocationSerializer;
import me.blueslime.polarwarps.utils.SoundController;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleEffect;

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
                    messages.getString("messages.set-warp-usage", "&bUsage: &e/setwarp (warp name)&b.")
            );
            return;
        }

        if (args.length == 1 && sender.hasPermission("polarwarps.warp.set")) {

            Player player = ((SlimePlayer)sender).get();

            if (!plugin.getWarpCommand().getWarps().containsKey(args[0])) {
                plugin.getWarpCommand().getWarps().put(args[0], player.getLocation());

                ConfigurationHandler handler = plugin.getConfigurationHandler(SlimeFile.WARPS);

                String path = "warps." + args[0] + ".";

                handler.set(
                        path + "location",
                        LocationSerializer.toString(player.getLocation())
                );


                handler.set(
                        path + "permission.enabled",
                        false
                );

                handler.set(
                        path + "permission.value",
                        "warps." + args[0]
                );

                handler.set(
                        path + "permission.value-other",
                        path + "other"
                );

                handler.set(
                        path + "delay",
                        10
                );

                handler.set(
                        path + "custom-sound.enabled",
                        false
                );

                handler.set(
                        path + "custom-particle.enabled",
                        false
                );

                ParticleEffect particle = SoundController.getRandomEnum(ParticleEffect.class);

                Sound sound = SoundController.getRandomEnum(Sound.class);

                handler.set(
                        path + "custom-sound.value",
                        sound.toString().toUpperCase()
                );

                handler.set(
                        path + "custom-particle.value",
                        particle.toString().toUpperCase()
                );

                List<String> welcomeList = plugin.getConfigurationHandler(SlimeFile.SETTINGS).getStringList("default-welcome-message");

                handler.set(
                        path + "welcome-message",
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
