package me.m56738.easyarmorstands.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class TownyAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private TownyPrivilegeChecker privilegeChecker;

    public TownyAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Towny";
    }

    @Override
    public void enable() {
        privilegeChecker = new TownyPrivilegeChecker(TownyAPI.getInstance());
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
