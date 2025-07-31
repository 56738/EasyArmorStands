package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class ResidenceAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private ResidencePrivilegeChecker privilegeChecker;

    public ResidenceAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Residence";
    }

    @Override
    public void enable() {
        privilegeChecker = new ResidencePrivilegeChecker(Residence.getInstance());
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
