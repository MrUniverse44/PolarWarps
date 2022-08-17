package me.blueslime.polarwarps.provider;

import org.bukkit.entity.Player;

public class DefaultProvider implements MessageProvider {

    @Override
    public String replace(Player player, String message) {
        return message.replace("%player%", player.getName());
    }

}
