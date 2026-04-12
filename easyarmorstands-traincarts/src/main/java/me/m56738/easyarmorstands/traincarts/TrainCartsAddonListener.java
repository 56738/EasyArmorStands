package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.api.event.menu.ElementMenuOpenEvent;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TrainCartsAddonListener implements Listener {
    @EventHandler
    public void onOpen(ElementMenuOpenEvent event) {
        if (event.getPlayer().hasPermission(Permissions.TRAINCARTS_MODEL)) {
            event.getBuilder().addButton(new TrainCartsModelListingButton(event.getElement()));
        }
    }
}
