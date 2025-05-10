package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class BentoBoxAddonFactory implements AddonFactory<BentoBoxAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.bentoBox.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BentoBox") != null;
    }

    @Override
    public BentoBoxAddon create() {
        return new BentoBoxAddon();
    }
}
