package me.m56738.easyarmorstands.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class TownyAddon implements Addon {
    private TownyPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "Towny";
    }

    @Override
    public void enable() {
        privilegeChecker = new TownyPrivilegeChecker(TownyAPI.getInstance());
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
