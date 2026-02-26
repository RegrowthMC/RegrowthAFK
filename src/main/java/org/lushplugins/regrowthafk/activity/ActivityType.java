package org.lushplugins.regrowthafk.activity;

import org.lushplugins.regrowthafk.RegrowthAFK;

public enum ActivityType {
    CHAT,
    COMMAND,
    INTERACT,
    MOVEMENT;

    public int points() {
        return RegrowthAFK.getInstance().getConfigManager().getActivityPoints(this);
    }
}
