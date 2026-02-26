package org.lushplugins.regrowthafk.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.lushplugins.regrowthafk.RegrowthAFK;
import org.lushplugins.regrowthafk.activity.ActivityStatus;
import org.lushplugins.regrowthafk.activity.ActivityTracker;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@SuppressWarnings("unused")
@Command("afk")
public class AFKCommand {

    @Command("afk")
    @CommandPermission("afk.set")
    public void afk(
        BukkitCommandActor actor,
        @Suggest({"on", "off", "toggle"}) @Optional String state,
        @CommandPermission("afk.set.other") @Optional Player target
    ) {
        if (target == null) {
            target = actor.requirePlayer();
        }

        ActivityTracker tracker = RegrowthAFK.getInstance().getActivityManager().getTracker(target.getUniqueId());
        switch (state) {
            case "on" -> tracker.setStatus(ActivityStatus.AFK);
            case "off" -> tracker.setStatus(ActivityStatus.ACTIVE);
            case null, default -> tracker.setStatus(tracker.isAfk() ? ActivityStatus.ACTIVE : ActivityStatus.AFK);
        }
    }

    @Subcommand("reload")
    @CommandPermission("afk.reload")
    public void reload(CommandSender sender) {
        RegrowthAFK.getInstance().getConfigManager().reload();

        sender.sendMessage(Component.text()
            .content("RegrowthAFK reloaded!")
            .color(TextColor.fromHexString("#b7faa2"))
            .build());
    }
}
