package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class BentoBoxAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private BentoBoxPrivilegeChecker privilegeChecker;

    public BentoBoxAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "BentoBox";
    }

    @Override
    public void enable() {
        privilegeChecker = new BentoBoxPrivilegeChecker();
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
