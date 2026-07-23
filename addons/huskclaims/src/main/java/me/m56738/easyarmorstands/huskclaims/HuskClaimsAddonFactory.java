package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class HuskClaimsAddonFactory implements AddonFactory<HuskClaimsAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.huskClaims.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HuskClaims") != null;
    }

    @Override
    public HuskClaimsAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new HuskClaimsAddon(plugin, eas);
    }
}
