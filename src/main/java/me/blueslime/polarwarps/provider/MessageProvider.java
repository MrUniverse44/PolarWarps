package me.blueslime.polarwarps.provider;

import org.bukkit.entity.Player;

public interface MessageProvider {
    String replace(Player player, String message);
}
