package org.lushplugins.regrowthafk.activity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.lushplugins.lushlib.libraries.chatcolor.ModernChatColorHandler;
import org.lushplugins.lushlib.utils.TimeFormatter;
import org.lushplugins.regrowthafk.RegrowthAFK;
import org.lushplugins.regrowthafk.config.ConfigManager;

import java.time.Duration;
import java.util.UUID;

public class ActivityTracker {
    private final UUID uuid;
    private ActivityStatus status;
    private int points;
    private long trackingTimestamp;
    private Long idleStartTimestamp;
    private Long afkStartTimestamp;
    private boolean bypassedKick;

    public ActivityTracker(UUID uuid) {
        this.uuid = uuid;
        this.status = ActivityStatus.ACTIVE;
        this.points = 0;
        this.trackingTimestamp = System.currentTimeMillis();
        this.bypassedKick = false;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public boolean isAfk() {
        return status == ActivityStatus.AFK;
    }

    public void setStatus(ActivityStatus status, boolean silent) {
        long now = System.currentTimeMillis();
        Duration duration = this.afkStartTimestamp != null ? Duration.ofMillis(now - this.afkStartTimestamp) : null;

        switch (status) {
            case ACTIVE -> {
                this.idleStartTimestamp = null;
                this.afkStartTimestamp = null;
                this.bypassedKick = false;
            }
            case AFK -> {
                this.afkStartTimestamp = now;

                if (this.idleStartTimestamp == null) {
                    this.idleStartTimestamp = now;
                }
            }
        }

        if (this.status == status) {
            return;
        }

        this.status = status;
        if (!silent) {
            String statusName = status.name().toLowerCase();
            Player player = Bukkit.getPlayer(this.uuid);
            if (player != null) {
                String message = RegrowthAFK.getInstance().getConfigManager().getMessage("now-" + statusName)
                    .replace("%player%", player.getName());

                if (duration != null) {
                    message = message.replace("%time%", TimeFormatter.formatDuration(duration, TimeFormatter.FormatType.ABBREVIATED_FORM));
                }

                Bukkit.broadcast(ModernChatColorHandler.translate(message, player));
            }
        }
    }

    public void setStatus(ActivityStatus status) {
        setStatus(status, false);
    }

    public void applyPoints(int points) {
        long now = System.currentTimeMillis();
        ConfigManager configManager = RegrowthAFK.getInstance().getConfigManager();
        if (now - this.trackingTimestamp > configManager.getActivityPointTimeout()) {
            this.trackingTimestamp = now;
            this.points = 0;
        }

        this.points += points;
        if (this.points >= configManager.getActivityPointThreshold()) {
            setStatus(ActivityStatus.ACTIVE);
            return;
        }

        if (this.idleStartTimestamp == null) {
            this.idleStartTimestamp = now;
        } else if (this.status != ActivityStatus.AFK && (now - this.idleStartTimestamp) > configManager.getIdleTime()) {
            setStatus(ActivityStatus.AFK);
            this.afkStartTimestamp = now;
        } else if (this.status == ActivityStatus.AFK && !this.bypassedKick && (now - this.afkStartTimestamp) > configManager.getKickTime()) {
            Player player = Bukkit.getPlayer(this.uuid);
            if (player != null) {
                if (player.hasPermission("afk.kickbypass")) {
                    this.bypassedKick = true;
                } else {
                    Bukkit.getScheduler().runTask(RegrowthAFK.getInstance(), () -> {
                        player.kick(ModernChatColorHandler.translate(configManager.getMessage("afk-kick")));
                    });
                }
            }
        }
    }

    public void applyPoints(ActivityType type) {
        applyPoints(type.points());
    }

    public void tick() {
        // Applying zero points runs necessary checks
        applyPoints(0);
    }
}
