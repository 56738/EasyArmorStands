package me.m56738.easyarmorstands.fancyholograms;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementDiscoverySource;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementType;
import org.bukkit.event.HandlerList;

public class FancyHologramsAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;

    private FancyHologramsListener listener;

    public FancyHologramsAddon() {
        this.plugin = EasyArmorStandsPlugin.getInstance();
    }

    @Override
    public String name() {
        return "FancyHolograms";
    }

    @Override
    public void enable() {
        // TODO property types
        // TODO spawn

        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        HologramElementType type = new HologramElementType(manager, this);
        HologramElementDiscoverySource discoverySource = new HologramElementDiscoverySource(type, manager);
        listener = new FancyHologramsListener(discoverySource);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
        listener = null;
    }

    @Override
    public void reload() {
    }
}
