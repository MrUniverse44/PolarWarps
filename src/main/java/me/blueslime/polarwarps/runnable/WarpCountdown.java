package me.blueslime.polarwarps.runnable;

import dev.mruniverse.slimelib.colors.platforms.bungeecord.BungeeSlimeColor;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.polarwarps.PolarWarps;
import me.blueslime.polarwarps.SlimeFile;
import me.blueslime.polarwarps.commands.warp.Warp;
import me.blueslime.polarwarps.provider.MessageProvider;
import me.blueslime.polarwarps.utils.LocationSerializer;
import me.blueslime.polarwarps.utils.MessageSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class WarpCountdown extends BukkitRunnable {
    private final ConfigurationHandler messages;
    private final List<String> list;
    private final Location location;
    private final boolean hasSound;
    private final Location last;
    private final Player player;
    private final Warp warp;
    private final Sound sound;
    private int delay;

    public WarpCountdown(Warp warp, ConfigurationHandler messages, Player player, int delay, Location location, List<String> list, boolean hasSound, Sound sound) {
        this.messages = messages;
        this.location = location;
        this.player   = player;
        this.warp     = warp;
        this.delay    = delay;
        this.list     = list;
        this.last     = player.getLocation();
        this.sound    = sound;
        this.hasSound = hasSound;
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

            String messageType = settings.getString("settings.time-left-type", "ACTION_BAR").toLowerCase();

            if (x.equals(nX) && z.equals(nZ)) {

                String text = messages.getString("messages.sending-message", "&aTime Left: &f%left%")
                                .replace("%left%", delay + "");

                text = warp.getPlugin().getMessage().replace(
                        player,
                        text
                );

                MessageSender.send(
                        warp.getPlugin().isUsingComponents(),
                        messageType,
                        player,
                        text
                );
            } else {

                String text = messages.getString("messages.sending-cancelled", "&cYou moved! Teleport cancelled")
                                .replace("%left%", delay + "");

                text = warp.getPlugin().getMessage().replace(
                        player,
                        text
                );

                MessageSender.send(
                        warp.getPlugin().isUsingComponents(),
                        messageType,
                        player,
                        text
                );

                warp.removeCountdown(player);

                cancel();
            }
        } else {
            if (location != null && location.getWorld() != null && location.isWorldLoaded()) {
                try {
                    player.teleport(
                            location
                    );
                    if (hasSound) {
                        player.playSound(
                                player,
                                sound,
                                1F,
                                0.5F
                        );
                    }
                } catch (IllegalArgumentException ignored) {
                    warp.removeCountdown(player);
                    cancel();
                }
            } else {
                warp.getPlugin().getLogs().info("Can't teleport player to an specified warp on this moment");
            }

            warp.removeCountdown(player);

            boolean comp = warp.getPlugin().isUsingComponents();

            MessageProvider provider = warp.getPlugin().getMessage();

            if (!list.contains("<disable>")) {
                for (String text : list) {
                    if (!text.contains("<actionbar>")) {
                        if (comp) {
                            player.spigot().sendMessage(
                                    new BungeeSlimeColor(
                                            provider.replace(
                                                    player,
                                                    text
                                            ),
                                            true
                                    ).build()
                            );
                        } else {
                            player.sendMessage(
                                    color(
                                            provider.replace(
                                                    player,
                                                    text
                                            )
                                    )
                            );
                        }
                    } else {
                        TextComponent component = new TextComponent(
                                color(
                                        provider.replace(
                                                player,
                                                text.replace("<actionbar>", "")
                                        )
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
