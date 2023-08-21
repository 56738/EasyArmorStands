package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.event.menu.EntityElementMenuInitializeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TrainCartsListener implements Listener {
    private final TrainCartsAddon addon;

    public TrainCartsListener(TrainCartsAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void onMenuInitialize(EntityElementMenuInitializeEvent event) {
        MenuElement element = event.getElement();
        if (element.hasItemSlots() && event.getPlayer().hasPermission("easyarmorstands.traincarts.model")) {
            event.getMenuBuilder().addUtility(new TrainCartsModelListingSlot(addon, element));
        }
    }
}
