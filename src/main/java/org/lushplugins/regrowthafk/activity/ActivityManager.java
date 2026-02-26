package org.lushplugins.regrowthafk.activity;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.lushplugins.regrowthafk.RegrowthAFK;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActivityManager {
    private final Map<UUID, ActivityTracker> trackers = new HashMap<>();
    private final BukkitTask task;

    public ActivityManager() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(RegrowthAFK.getInstance(), () -> {
            for (ActivityTracker tracker : this.trackers.values()) {
                tracker.tick();
            }
        }, 60, 60);
    }

    public ActivityTracker getTracker(UUID uuid) {
        return this.trackers.get(uuid);
    }

    public void startTracking(UUID uuid) {
        this.trackers.put(uuid, new ActivityTracker(uuid));
    }

    public void stopTracking(UUID uuid) {
        this.trackers.remove(uuid);
    }

    public void shutdown() {
        this.task.cancel();
    }
}
