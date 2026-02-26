package org.lushplugins.regrowthafk.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.lushplugins.lushlib.utils.YamlUtils;
import org.lushplugins.regrowthafk.RegrowthAFK;
import org.lushplugins.regrowthafk.activity.ActivityStatus;
import org.lushplugins.regrowthafk.activity.ActivityType;

import java.util.Map;
import java.util.stream.Collectors;

public class ConfigManager {
    private long idleTime;
    private long kickTime;
    private int activityPointThreshold;
    private long activityPointTimeout;
    private Map<ActivityType, Integer> activityPoints;
    private Map<ActivityStatus, String> placeholders;
    private Map<String, String> messages;

    public ConfigManager() {
        RegrowthAFK.getInstance().saveDefaultConfig();
    }

    public void reload() {
        RegrowthAFK.getInstance().reloadConfig();
        FileConfiguration config = RegrowthAFK.getInstance().getConfig();

        this.idleTime = config.getInt("idle-time") * 1000L;
        this.kickTime = config.getInt("kick-time") * 1000L;
        this.activityPointThreshold = config.getInt("activity-points.threshold");
        this.activityPointTimeout = config.getInt("activity-points.timeout") * 1000L;

        this.activityPoints = YamlUtils.getMap(config, "activity-points.triggers", Integer.class).entrySet().stream()
            .collect(Collectors.toMap(
                entry -> ActivityType.valueOf(entry.getKey().toUpperCase()),
                Map.Entry::getValue
            ));
        this.placeholders = YamlUtils.getMap(config, "placeholders", String.class).entrySet().stream()
            .collect(Collectors.toMap(
                entry -> ActivityStatus.valueOf(entry.getKey().toUpperCase()),
                Map.Entry::getValue
            ));
        this.messages = YamlUtils.getMap(config, "messages", String.class);
    }

    public long getIdleTime() {
        return idleTime;
    }

    public long getKickTime() {
        return kickTime;
    }

    public int getActivityPointThreshold() {
        return activityPointThreshold;
    }

    public long getActivityPointTimeout() {
        return activityPointTimeout;
    }

    public int getActivityPoints(ActivityType type) {
        return this.activityPoints.getOrDefault(type, 0);
    }

    public String getStatusPlaceholder(ActivityStatus status) {
        return this.placeholders.getOrDefault(status, "");
    }

    public String getMessage(String id) {
        return this.messages.get(id);
    }
}
