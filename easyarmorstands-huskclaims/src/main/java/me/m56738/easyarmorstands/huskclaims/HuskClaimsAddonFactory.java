package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class HuskClaimsAddonFactory implements AddonFactory<HuskClaimsAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HuskClaims") != null;
    }

    @Override
    public HuskClaimsAddon create(EasyArmorStandsPlugin plugin) {
        return new HuskClaimsAddon(plugin);
    }
}
