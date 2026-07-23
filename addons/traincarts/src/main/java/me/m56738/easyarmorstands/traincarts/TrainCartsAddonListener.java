package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.paper.api.event.menu.ElementMenuOpenEvent;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TrainCartsAddonListener implements Listener {
    private final Plugin plugin;
    private final EasyArmorStandsCommon eas;

    public TrainCartsAddonListener(Plugin plugin, EasyArmorStandsCommon eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @EventHandler
    public void onOpen(ElementMenuOpenEvent event) {
        if (event.getPlayer().hasPermission(Permissions.TRAINCARTS_MODEL)) {
            event.getBuilder().addButton(new TrainCartsModelListingButton(plugin, eas, event.getElement()));
        }
    }
}
