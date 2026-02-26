package org.lushplugins.regrowthafk.activity;

import org.lushplugins.regrowthafk.RegrowthAFK;

public enum ActivityStatus {
    ACTIVE,
    AFK;

    public String placeholder() {
        return RegrowthAFK.getInstance().getConfigManager().getStatusPlaceholder(this);
    }
}
