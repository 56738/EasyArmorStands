package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class BentoBoxAddonFactory implements AddonFactory<BentoBoxAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BentoBox") != null;
    }

    @Override
    public BentoBoxAddon create(EasyArmorStandsPlugin plugin) {
        return new BentoBoxAddon(plugin);
    }
}
