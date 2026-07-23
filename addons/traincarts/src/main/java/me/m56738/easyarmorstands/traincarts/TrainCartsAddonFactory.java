package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TrainCartsAddonFactory implements AddonFactory<TrainCartsAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.trainCarts.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Train_Carts") != null
                && ReflectionUtil.hasClass("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult");
    }

    @Override
    public TrainCartsAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new TrainCartsAddon(plugin, eas);
    }
}
