package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class ResidenceAddon implements Addon {
    private ResidencePrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "Residence";
    }

    @Override
    public void enable() {
        privilegeChecker = new ResidencePrivilegeChecker(Residence.getInstance());
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
