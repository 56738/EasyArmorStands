package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.event.SessionMenuInitializeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TrainCartsListener implements Listener {
    @EventHandler
    public void onMenuInitialize(SessionMenuInitializeEvent event) {
        if (event.getPlayer().hasPermission("easyarmorstands.traincarts.model")) {
            event.getMenu().addShortcut(new TrainCartsModelListingSlot(event.getMenu()));
        }
    }
}
