package me.blueslime.polarwarps.provider;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderProvider implements MessageProvider {

    @Override
    public String replace(Player player, String message) {
        return PlaceholderAPI.setPlaceholders(
                player,
                message.replace("%player%", player.getName())
        );
    }

}
