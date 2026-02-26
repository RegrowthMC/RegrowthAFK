package org.lushplugins.regrowthafk;

import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.placeholderhandler.PlaceholderHandler;
import org.lushplugins.regrowthafk.activity.ActivityManager;
import org.lushplugins.regrowthafk.command.AFKCommand;
import org.lushplugins.regrowthafk.config.ConfigManager;
import org.lushplugins.regrowthafk.listener.ActivityListener;
import org.lushplugins.regrowthafk.listener.PlayerListener;
import org.lushplugins.regrowthafk.placeholder.Placeholders;
import revxrsal.commands.bukkit.BukkitLamp;

public final class RegrowthAFK extends SpigotPlugin {
    private static RegrowthAFK plugin;

    private ConfigManager configManager;
    private ActivityManager activityManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager();
        this.configManager.reload();
        this.activityManager = new ActivityManager();

        registerListeners(
            new ActivityListener(),
            new PlayerListener()
        );

        BukkitLamp.builder(this)
            .build()
            .register(new AFKCommand());

        PlaceholderHandler.builder(this)
            .build()
            .register(new Placeholders());
    }

    @Override
    public void onDisable() {
        if (this.activityManager != null) {
            this.activityManager.shutdown();
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    public static RegrowthAFK getInstance() {
        return plugin;
    }
}
