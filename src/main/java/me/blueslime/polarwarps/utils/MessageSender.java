package me.blueslime.polarwarps.utils;

import dev.mruniverse.slimelib.colors.SlimeColor;
import dev.mruniverse.slimelib.colors.platforms.bungeecord.BungeeSlimeColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class MessageSender {

    public static void send(boolean useComponents, String type, Player player, String text) {

        text = color(
                text
        );

        switch (type) {
            default:
            case "action-bar":
            case "action_bar":
            case "actionbar":
            case "ab":
            case "action":
            case "3":
                TextComponent component = new TextComponent(
                        text
                );

                player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        component
                );
                break;
            case "subtitle":
            case "sub_title":
            case "sub-title":
            case "sub":
            case "st":
            case "2":
                player.sendTitle(
                        "",
                        text,
                        10,
                        25,
                        10
                );
                break;
            case "title":
            case "tt":
            case "1":
                player.sendTitle(
                        text,
                        "",
                        10,
                        25,
                        10
                );
                break;
            case "message":
            case "text":
            case "msg":
            case "0":
                if (!useComponents) {
                    player.sendMessage(
                            text
                    );
                } else {
                    BaseComponent textComponent = new BungeeSlimeColor(
                            text,
                            true,
                            SlimeColor.ColorMethod.ALL
                    ).build();

                    player.spigot().sendMessage(
                            textComponent
                    );
                }
                break;
        }
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
