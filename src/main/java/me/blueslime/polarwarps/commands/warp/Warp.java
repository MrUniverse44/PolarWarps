package me.blueslime.polarwarps.commands.warp;

import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.source.SlimeSource;
import dev.mruniverse.slimelib.source.player.SlimePlayer;
import me.blueslime.polarwarps.SlimeFile;
import me.blueslime.polarwarps.runnable.WarpCountdown;
import me.blueslime.polarwarps.utils.LocationSerializer;
import me.blueslime.polarwarps.utils.SoundController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import me.blueslime.polarwarps.PolarWarps;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Command(
        description = "Warp command",
        shortDescription = "Warp",
        usage = "/warp (warp name)"
)
public final class Warp implements SlimeCommand {
    private final Map<String, Location> warps = new ConcurrentHashMap<>();

    private final Map<UUID, Integer> counts = new ConcurrentHashMap<>();

    private final PolarWarps plugin;

    public Warp(PolarWarps plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        ConfigurationHandler configuration = plugin.getConfigurationHandler(SlimeFile.WARPS);

        warps.clear();

        for (String warp : configuration.getContent("warps", false)) {
            Location location = LocationSerializer.fromString(
                    configuration.getString("warps." + warp + ".location", "world, 0, 0, 0, 0, 0")
            );

            warps.put(
                    warp,
                    location
            );
        }
    }

    public void reload() {
        load();
    }

    @Override
    public String getCommand() {
        return "warp";
    }

    @Override
    public void execute(SlimeSource sender, String commandLabel, String[] args) {

        ConfigurationHandler messages = plugin.getConfigurationHandler(SlimeFile.MESSAGES);
        ConfigurationHandler settings = plugin.getConfigurationHandler(SlimeFile.WARPS);

        if (!sender.isPlayer()) {
            sender.sendColoredMessage(
                    messages.getString("messages.console-error", "&cThis command is only for players")
            );
            return;
        }

        if (args.length == 0) {
            sender.sendColoredMessage(
                    messages.getString("messages.usage", "&bUsage: &e/warp (warp name)&b.")
            );
            return;
        }

        Player player;

        String permission;

        String warp = args[0];
        String path = "warps." + warp + ".";

        if (args.length == 1) {
            permission = settings.getString(path + "permission.value", "warps." + warp);
            player = ((SlimePlayer)sender).get();
        } else {
            permission = settings.getString(path + "permission.value-other", "warps." + warp + ".other");
            player = Bukkit.getPlayer(args[1]);
        }

        if (player == null) {
            sender.sendColoredMessage(
                    messages.getString("messages.player-not-online", "&cThis player is not online!")
            );
            return;
        }

        if (sender.getName().equals(player.getName())) {
            if (settings.getStatus(path + "permission.enabled", false) && !sender.hasPermission(permission)) {
                sender.sendColoredMessage(
                        messages.getString("messages.no-warp-permission", "&cYou don't have permissions to travel to this warp.")
                );
                return;
            }
        } else {
            if (!sender.hasPermission(permission)) {
                sender.sendColoredMessage(
                        messages.getString("messages.no-warp-permission", "&cYou don't have permissions to travel to this warp.")
                );
                return;
            }
        }

        Location location = warps.get(warp);

        if (location == null) {
            sender.sendColoredMessage(
                    messages.getString("messages.warp-error", "&cThis warp doesn't exists.")
            );
            return;
        }

        sendWarpCountdown(
                player,
                warp,
                settings.getInt(path + "delay", 10),
                location
        );
    }

    public Map<String, Location> getWarps() {
        return warps;
    }

    public void sendWarpCountdown(final Player player, String warp, final int delay, final Location location) {
        if (counts.containsKey(player.getUniqueId())) {
            plugin.getServer().getScheduler().cancelTask(
                    counts.get(player.getUniqueId())
            );
            counts.remove(player.getUniqueId());
        }
        ConfigurationHandler warps = plugin.getConfigurationHandler(SlimeFile.WARPS);
        WarpCountdown countdown = new WarpCountdown(
                this,
                plugin.getConfigurationHandler(SlimeFile.MESSAGES),
                player,
                delay,
                location,
                warps.getStringList("warps." + warp + ".welcome-message"),
                warps.getBoolean("warps." + warp + ".custom-sound.enabled", false),
                verifySound(
                        warps.getString(
                                "warps." + warp + ".custom-sound.value",
                                SoundController.getRandomEnumString(Sound.class)
                        )
                ),
                warps.getBoolean("warps." + warp + ".custom-particle.enabled", false),
                verifyParticle(
                        warps.getString(
                                "warps." + warp + ".custom-particle.value",
                                SoundController.getRandomEnumString(ParticleEffect.class)
                        )
                )
        );

        counts.put(player.getUniqueId(), countdown.getTaskId());
    }

    public Sound verifySound(String sound) {
        try {
            return Sound.valueOf(sound);
        } catch (IllegalArgumentException ignored) {
            return SoundController.getRandomEnum(Sound.class);
        }
    }

    public ParticleEffect verifyParticle(String sound) {
        try {
            return ParticleEffect.valueOf(sound);
        } catch (IllegalArgumentException ignored) {
            return SoundController.getRandomEnum(ParticleEffect.class);
        }
    }

    @Override
    public List<String> onTabComplete(SlimeSource sender, String commandLabel, String[] args) {

        List<String> list = new ArrayList<>();

        if (args.length == 0) {
            list.addAll(
                    warps.keySet()
            );
        } else if (args.length == 1) {
            list.add(
                    sender.getName()
            );
        }
        return list;
    }

    public void removeCountdown(final Player player) {
        counts.remove(player.getUniqueId());
    }

    public PolarWarps getPlugin() {
        return plugin;
    }
}
