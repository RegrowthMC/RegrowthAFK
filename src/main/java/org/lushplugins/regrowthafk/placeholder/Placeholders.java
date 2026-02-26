package org.lushplugins.regrowthafk.placeholder;

import org.bukkit.entity.Player;
import org.lushplugins.placeholderhandler.annotation.Placeholder;
import org.lushplugins.placeholderhandler.annotation.SubPlaceholder;
import org.lushplugins.regrowthafk.RegrowthAFK;

@SuppressWarnings("unused")
@Placeholder("afk")
public class Placeholders {

    @SubPlaceholder("status")
    public String status(Player player) {
        return RegrowthAFK.getInstance().getActivityManager().getTracker(player.getUniqueId()).getStatus().placeholder();
    }
}
