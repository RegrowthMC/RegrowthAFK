package org.lushplugins.regrowthafk;

import org.bukkit.plugin.java.JavaPlugin;

public final class RegrowthAfk extends JavaPlugin {
    private static RegrowthAfk plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static RegrowthAfk getInstance() {
        return plugin;
    }
}
