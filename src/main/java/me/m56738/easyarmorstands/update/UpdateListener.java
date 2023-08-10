package me.m56738.easyarmorstands.update;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
    private final UpdateManager updateManager;
    private final BukkitAudiences adventure;

    public UpdateListener(UpdateManager updateManager, BukkitAudiences adventure) {
        this.updateManager = updateManager;
        this.adventure = adventure;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("easyarmorstands.update.notify")) {
            return;
        }
        Audience audience = adventure.player(player);
        updateManager.notify(audience);
    }
}
