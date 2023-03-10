package me.m56738.easyarmorstands.addon.traincarts;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;

public class TrainCartsAddon implements Addon {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("com.bergerkiller.bukkit.common.PluginBase");
            Class.forName("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult");
            return true;
        } catch (Throwable e) {
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
        TrainCartsListener listener = new TrainCartsListener(integration);
        plugin.getAnnotationParser().parse(integration);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
