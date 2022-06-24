package p1xel.minecraft.Storage;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.minecraft.EasyWarp;

import java.io.File;
import java.io.IOException;

public class Warps {

    public static void createFile() {
        File file = new File(EasyWarp.getInstance().getDataFolder(), "warps.yml");
        if (!file.exists()) {
            EasyWarp.getInstance().saveResource("warps.yml", false);
        }
    }

    public static FileConfiguration get() {
        File file = new File(EasyWarp.getInstance().getDataFolder(), "warps.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(EasyWarp.getInstance().getDataFolder(), "warps.yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isWarpExist(String warp) {
        return get().isSet(warp);
    }

    public static String getWarpLabel(String warp) {
        return get().getString(warp + ".label");
    }

    public static Location getWarpLoc(String warp) {
        return (Location) get().get(warp + ".loc");
    }

    public static void createWarp(String warp, Location loc) {
        set(warp + ".label", warp);
        set(warp + ".loc", loc);
    }

    public static void removeWarp(String warp) {
        set(warp, null);
    }

    public static void setWarpLoc(String warp, Location loc) {
        set(warp + ".loc", loc);
    }

}
