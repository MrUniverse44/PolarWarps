package me.blueslime.polarwarps;

import dev.mruniverse.slimelib.SlimeFiles;
import dev.mruniverse.slimelib.SlimePlatform;
import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.commands.SlimeCommands;
import dev.mruniverse.slimelib.file.configuration.ConfigurationHandler;
import dev.mruniverse.slimelib.file.input.DefaultInputManager;
import dev.mruniverse.slimelib.loader.BaseSlimeLoader;
import dev.mruniverse.slimelib.loader.DefaultSlimeLoader;
import dev.mruniverse.slimelib.logs.SlimeLogger;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import me.blueslime.polarwarps.commands.PluginCommand;
import me.blueslime.polarwarps.commands.warp.DeleteWarp;
import me.blueslime.polarwarps.commands.warp.SetWarp;
import me.blueslime.polarwarps.commands.warp.Warp;
import me.blueslime.polarwarps.bstats.Metrics;

import me.blueslime.polarwarps.commands.warp.Warps;
import org.bukkit.plugin.java.JavaPlugin;

public class PolarWarps extends JavaPlugin implements SlimePlugin<JavaPlugin> {

    private BaseSlimeLoader<JavaPlugin> loader;

    private SlimePluginInformation information;

    private final SlimePlatform platform = SlimePlatform.SPIGOT;

    private Warp warpCommand;

    private SlimeLogs logs;

    @Override
    public void onEnable() {

        this.logs = SlimeLogger.createLogs(
                platform,
                this
        );

        this.logs.getProperties().getPrefixes().changeMainText("&fPolarWarps");

        this.information = new SlimePluginInformation(
                platform,
                this
        );

        this.loader = new DefaultSlimeLoader<>(
                this,
                new DefaultInputManager()
        );

        this.loader.setFiles(SlimeFile.class);

        this.loader.init();

        warpCommand = new Warp(this);

        getCommands().register(
                warpCommand
        );
        getCommands().register(
                new SetWarp(this)
        );
        getCommands().register(
                new DeleteWarp(this)
        );

        getCommands().register(
                new PluginCommand(this)
        );

        if (getConfigurationHandler(SlimeFile.SETTINGS).getStatus("settings.enable-warps-command", false)) {
            getCommands().register(
                    new Warps(this)
            );
        }

        logs.info("The plugin has been loaded correctly!");

        new Metrics(this, 16125);

    }

    public ConfigurationHandler getConfigurationHandler(SlimeFiles file) {
        return getLoader().getFiles().getConfigurationHandler(file);
    }

    private SlimeCommands<JavaPlugin> getCommands() {
        return getLoader().getCommands();
    }

    public Warp getWarpCommand() {
        return warpCommand;
    }

    @Override
    public SlimePluginInformation getPluginInformation() {
        return information;
    }

    @Override
    public BaseSlimeLoader<JavaPlugin> getLoader() {
        return loader;
    }

    @Override
    public SlimeLogs getLogs() {
        return logs;
    }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public void reload() {
        loader.reload();

        warpCommand.reload();
    }
}
