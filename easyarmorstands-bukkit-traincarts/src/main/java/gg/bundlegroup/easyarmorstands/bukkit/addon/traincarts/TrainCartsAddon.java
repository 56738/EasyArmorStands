package gg.bundlegroup.easyarmorstands.bukkit.addon.traincarts;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands;
import gg.bundlegroup.easyarmorstands.bukkit.addon.Addon;

public class TrainCartsAddon implements Addon {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("com.bergerkiller.bukkit.tc.attachments.ui.models.ResourcePackModelListing");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "TrainCarts";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        TrainCartsIntegration integration = new TrainCartsIntegration(plugin);
        plugin.getAnnotationParser().parse(integration);
    }
}
