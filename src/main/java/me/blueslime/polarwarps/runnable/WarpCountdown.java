package me.blueslime.polarwarps.runnable;

import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.polarwarps.PolarWarps;
import me.blueslime.polarwarps.SlimeFile;
import me.blueslime.polarwarps.commands.warp.Warp;
import me.blueslime.polarwarps.utils.LocationSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class WarpCountdown extends BukkitRunnable {
    private final ConfigurationHandler messages;
    private final List<String> list;
    private final Location location;
    private final Location last;
    private final Player player;
    private final Warp warp;
    private int delay;

    public WarpCountdown(Warp warp, ConfigurationHandler messages, Player player, int delay, Location location, List<String> list) {
        this.messages = messages;
        this.location = location;
        this.player   = player;
        this.warp     = warp;
        this.delay    = delay;
        this.list     = list;
        this.last     = player.getLocation();
        init(warp.getPlugin());
    }

    public void init(PolarWarps plugin) {
        runTaskTimer(
                plugin,
                0L,
                20L
        );
    }

    @Override
    public void run() {

        if (!player.isOnline()) {

            warp.removeCountdown(player);

            cancel();
        }

        if (delay >= 1) {
            delay--;

            String x = LocationSerializer.toDecimal(last.getX());
            String z = LocationSerializer.toDecimal(last.getZ());

            String nX = LocationSerializer.toDecimal(player.getLocation().getX());
            String nZ = LocationSerializer.toDecimal(player.getLocation().getZ());

            ConfigurationHandler settings = warp.getPlugin().getConfigurationHandler(SlimeFile.SETTINGS);

            if (x.equals(nX) && z.equals(nZ)) {

                String text = color(
                        messages.getString("messages.sending-message", "&aTime Left: &f%left%")
                                .replace("%left%", delay + "")
                );

                TextComponent component = new TextComponent(
                        text
                );

                if (settings.getStatus("settings.show-in-action-bar")) {
                    player.spigot().sendMessage(
                            ChatMessageType.ACTION_BAR,
                            component
                    );
                } else {
                    player.sendTitle(
                            "",
                            text,
                            0,
                            5,
                            0
                    );
                }
            } else {

                String text = color(
                        messages.getString("messages.sending-cancelled", "&cYou moved! Teleport cancelled")
                                .replace("%left%", delay + "")
                );

                TextComponent component = new TextComponent(
                        text
                );

                if (settings.getStatus("settings.show-in-action-bar")) {
                    player.spigot().sendMessage(
                            ChatMessageType.ACTION_BAR,
                            component
                    );
                } else {
                    player.sendTitle(
                            "",
                            text,
                            0,
                            5,
                            0
                    );
                }

                warp.removeCountdown(player);

                cancel();
            }
        } else {
            player.teleport(
                    location
            );

            warp.removeCountdown(player);

            if (!list.contains("<disable>")) {
                for (String text : list) {
                    if (!text.contains("<actionbar>")) {
                        player.sendMessage(
                                color(text)
                        );
                    } else {
                        TextComponent component = new TextComponent(
                                color(
                                        text.replace("<actionbar>", "")
                                )
                        );
                        player.spigot().sendMessage(
                                ChatMessageType.ACTION_BAR,
                                component
                        );
                    }
                }
            }

            cancel();
        }
    }

    private String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
