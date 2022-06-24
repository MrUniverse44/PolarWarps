package p1xel.minecraft.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import p1xel.minecraft.EasyWarp;

import java.util.List;

public class Config {

    public static String getString(String path) {
        return EasyWarp.getInstance().getConfig().getString(path);
    }

    public static List<String> getStringList(String path) { return EasyWarp.getInstance().getConfig().getStringList(path); }

    public static int getInt(String path) {
        return EasyWarp.getInstance().getConfig().getInt(path);
    }

    public static double getDouble(String path) {
        return EasyWarp.getInstance().getConfig().getDouble(path);
    }

    public static boolean getBool(String path) {
        return EasyWarp.getInstance().getConfig().getBoolean(path);
    }

    public static String getVersion() {
        return getString("Version");
    }

    public static String getLanguage() {
        return getString("Language");
    }

    public static FileConfiguration get() {
        return EasyWarp.getInstance().getConfig();
    }
    
}
