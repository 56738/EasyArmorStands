package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class HuskClaimsAddonFactory implements AddonFactory<HuskClaimsAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.huskClaims.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HuskClaims") != null;
    }

    @Override
    public HuskClaimsAddon create() {
        return new HuskClaimsAddon();
    }
}
