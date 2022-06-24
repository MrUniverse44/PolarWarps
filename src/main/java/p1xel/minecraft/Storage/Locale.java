package p1xel.minecraft.Storage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.minecraft.EasyWarp;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Locale {

    public static void createFile() {
        List<String> langs = Arrays.asList("en","zh_CN");
        for (String l : langs) {
            File file = new File(EasyWarp.getInstance().getDataFolder(), l + ".yml");
            if (!file.exists()) {
                EasyWarp.getInstance().saveResource(l + ".yml", false);
            }
        }
    }

    public static FileConfiguration get() {
        File file = new File(EasyWarp.getInstance().getDataFolder(), Config.getLanguage() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(EasyWarp.getInstance().getDataFolder(), Config.getLanguage() + ".yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', get().getString(path).replaceAll("%prefix%", get().getString("Prefix")));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%prefix%", get().getString("Prefix")));
    }
    
}
