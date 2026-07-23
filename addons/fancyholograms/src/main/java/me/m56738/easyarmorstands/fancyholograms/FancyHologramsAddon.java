package me.m56738.easyarmorstands.fancyholograms;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementDiscoverySource;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramPropertyTypes;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class FancyHologramsAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;

    private FancyHologramsListener listener;

    public FancyHologramsAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "FancyHolograms";
    }

    @Override
    public void enable() {
        eas.propertyTypeRegistry().register(HologramPropertyTypes.DATA);
        eas.propertyTypeRegistry().register(HologramPropertyTypes.TEXT);

        HologramManager manager = FancyHologramsPlugin.get().getHologramManager();
        HologramElementType type = new HologramElementType(eas, manager, this);
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
