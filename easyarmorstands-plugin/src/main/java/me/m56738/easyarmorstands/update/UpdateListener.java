package me.m56738.easyarmorstands.update;

import me.m56738.easyarmorstands.common.permission.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
    private final UpdateManager updateManager;

    public UpdateListener(UpdateManager updateManager) {
        this.updateManager = updateManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission(Permissions.UPDATE_NOTIFY)) {
            return;
        }
        updateManager.notify(player);
    }
}
