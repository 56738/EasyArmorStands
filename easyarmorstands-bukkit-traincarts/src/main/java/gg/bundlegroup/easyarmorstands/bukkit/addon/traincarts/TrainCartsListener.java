package gg.bundlegroup.easyarmorstands.bukkit.addon.traincarts;

import gg.bundlegroup.easyarmorstands.bukkit.event.SessionMenuInitializeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TrainCartsListener implements Listener {
    private final TrainCartsIntegration integration;

    public TrainCartsListener(TrainCartsIntegration integration) {
        this.integration = integration;
    }

    @EventHandler
    public void onMenuInitialize(SessionMenuInitializeEvent event) {
        if (event.getPlayer().hasPermission("easyarmorstands.traincarts.model")) {
            event.getMenu().addEquipmentButton(new TrainCartsModelListingSlot(integration, event.getMenu()));
        }
    }
}
