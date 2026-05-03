package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.Bukkit;

public class TrainCartsAddonFactory implements AddonFactory<TrainCartsAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.trainCarts.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Train_Carts") != null
                && ReflectionUtil.hasClass("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult");
    }

    @Override
    public TrainCartsAddon create() {
        return new TrainCartsAddon();
    }
}
