package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.common.util.ReflectionUtil;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class TrainCartsAddonFactory implements AddonFactory<TrainCartsAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Train_Carts") != null
                && ReflectionUtil.hasClass("com.bergerkiller.bukkit.tc.attachments.ui.models.listing.DialogResult");
    }

    @Override
    public TrainCartsAddon create(EasyArmorStandsPlugin plugin) {
        return new TrainCartsAddon(plugin);
    }
}
