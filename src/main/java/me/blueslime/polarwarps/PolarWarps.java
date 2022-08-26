package me.blueslime.polarwarps;

import dev.mruniverse.slimelib.SlimePlatform;
import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.exceptions.SlimeLoaderException;
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
import me.blueslime.polarwarps.provider.DefaultProvider;
import me.blueslime.polarwarps.provider.MessageProvider;
import me.blueslime.polarwarps.provider.PlaceholderProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PolarWarps extends JavaPlugin implements SlimePlugin<JavaPlugin> {

    private BaseSlimeLoader<JavaPlugin> loader;

    private SlimePluginInformation information;

    private final SlimePlatform platform = SlimePlatform.SPIGOT;

    private MessageProvider provider;

    private boolean useComponents = false;

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

        try {
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
        } catch (SlimeLoaderException e) {
            logs.error(e);
        }

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            provider = new PlaceholderProvider();
        } else {
            provider = new DefaultProvider();
        }

        useComponents = getConfigurationHandler(SlimeFile.SETTINGS).getStatus("settings.use-components", false);

        logs.info("The plugin has been loaded correctly!");

        new Metrics(this, 16125);

        getServer().getScheduler().runTaskLater(
                this,
                () -> {
                    getWarpCommand().reload();
                },
                20L
        );

    }

    public Warp getWarpCommand() {
        return warpCommand;
    }

    public MessageProvider getMessage() {
        return provider;
    }

    public boolean isUsingComponents() {
        return useComponents;
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

        useComponents = getConfigurationHandler(SlimeFile.SETTINGS).getStatus("settings.use-components", false);

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            provider = new PlaceholderProvider();
        } else {
            provider = new DefaultProvider();
        }
    }
}
