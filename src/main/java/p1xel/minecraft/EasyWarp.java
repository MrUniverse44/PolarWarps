package p1xel.minecraft;

import org.bukkit.plugin.java.JavaPlugin;
import p1xel.minecraft.Commands.Cmd;
import p1xel.minecraft.Commands.DelWarpCmd;
import p1xel.minecraft.Commands.SetWarpCmd;
import p1xel.minecraft.Commands.WarpCmd;
import p1xel.minecraft.SpigotMc.UpdateChecker;
import p1xel.minecraft.Storage.Locale;
import p1xel.minecraft.Storage.Warps;
import p1xel.minecraft.bStats.Metrics;

public class EasyWarp extends JavaPlugin {

    private static EasyWarp instance;
    public static EasyWarp getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Locale.createFile();
        Warps.createFile();

        getServer().getPluginCommand("warp").setExecutor(new WarpCmd());
        getServer().getPluginCommand("setwarp").setExecutor(new SetWarpCmd());
        getServer().getPluginCommand("delwarp").setExecutor(new DelWarpCmd());
        getServer().getPluginCommand("EasyWarp").setExecutor(new Cmd());

        getLogger().info("Plugin loaded!");

        int pluginId = 15567;
        Metrics metrics = new Metrics(this, pluginId);

        new UpdateChecker(this, 102865).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });

    }

}
