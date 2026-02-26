package org.lushplugins.regrowthafk.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.lushplugins.regrowthafk.RegrowthAFK;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        RegrowthAFK.getInstance().getActivityManager().startTracking(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        RegrowthAFK.getInstance().getActivityManager().stopTracking(event.getPlayer().getUniqueId());
    }
}
