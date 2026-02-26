package org.lushplugins.regrowthafk.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.lushplugins.regrowthafk.RegrowthAFK;
import org.lushplugins.regrowthafk.activity.ActivityTracker;
import org.lushplugins.regrowthafk.activity.ActivityType;

public class ActivityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        ActivityTracker tracker = RegrowthAFK.getInstance().getActivityManager().getTracker(event.getPlayer().getUniqueId());
        tracker.applyPoints(ActivityType.CHAT);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        ActivityTracker tracker = RegrowthAFK.getInstance().getActivityManager().getTracker(event.getPlayer().getUniqueId());
        tracker.applyPoints(ActivityType.COMMAND);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            ActivityTracker tracker = RegrowthAFK.getInstance().getActivityManager().getTracker(event.getPlayer().getUniqueId());
            tracker.applyPoints(ActivityType.INTERACT);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.hasChangedBlock()) {
            ActivityTracker tracker = RegrowthAFK.getInstance().getActivityManager().getTracker(event.getPlayer().getUniqueId());
            tracker.applyPoints(ActivityType.MOVEMENT);
        }
    }
}
