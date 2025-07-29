package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class BentoBoxAddon implements Addon {
    private BentoBoxPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "BentoBox";
    }

    @Override
    public void enable() {
        privilegeChecker = new BentoBoxPrivilegeChecker();
        Plugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(privilegeChecker, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(privilegeChecker);
    }

    @Override
    public void reload() {
    }
}
