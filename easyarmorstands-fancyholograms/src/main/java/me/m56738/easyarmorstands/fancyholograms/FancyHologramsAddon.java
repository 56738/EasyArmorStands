package me.m56738.easyarmorstands.fancyholograms;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementDiscoverySource;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElementType;
import me.m56738.easyarmorstands.fancyholograms.property.type.HologramDataPropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.type.TextHologramTextPropertyType;
import org.bukkit.event.HandlerList;

public class FancyHologramsAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;

    private FancyHologramsListener listener;
    private MenuFactory itemMenuFactory;
    private MenuFactory blockMenuFactory;
    private MenuFactory textMenuFactory;

    public FancyHologramsAddon() {
        this.plugin = EasyArmorStandsPlugin.getInstance();
    }

    @Override
    public String name() {
        return "FancyHolograms";
    }

    @Override
    public void enable() {
        load();

        EasyArmorStandsPlugin.getInstance().propertyTypeRegistry().register(HologramDataPropertyType.INSTANCE);
        EasyArmorStandsPlugin.getInstance().propertyTypeRegistry().register(TextHologramTextPropertyType.INSTANCE);

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
        load();
    }

    private void load() {
        itemMenuFactory = plugin.loadMenuTemplate("fancyholograms/item");
        blockMenuFactory = plugin.loadMenuTemplate("fancyholograms/block");
        textMenuFactory = plugin.loadMenuTemplate("fancyholograms/text");
    }

    public MenuFactory getMenuFactory(HologramData data) {
        if (data instanceof ItemHologramData) {
            return itemMenuFactory;
        } else if (data instanceof BlockHologramData) {
            return blockMenuFactory;
        } else if (data instanceof TextHologramData) {
            return textMenuFactory;
        } else {
            return null;
        }
    }
}
