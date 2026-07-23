package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BentoBoxAddonFactory implements AddonFactory<BentoBoxAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.bentoBox.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("BentoBox") != null;
    }

    @Override
    public BentoBoxAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new BentoBoxAddon(plugin, eas);
    }
}
