package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.fancyholograms.FancyHologramsAddon;
import me.m56738.easyarmorstands.fancyholograms.property.HologramDataProperty;
import me.m56738.easyarmorstands.fancyholograms.property.HologramLocationProperty;
import me.m56738.easyarmorstands.fancyholograms.property.block.BlockHologramBlockProperty;
import me.m56738.easyarmorstands.fancyholograms.property.display.DisplayHologramBillboardProperty;
import me.m56738.easyarmorstands.fancyholograms.property.display.DisplayHologramScaleProperty;
import me.m56738.easyarmorstands.fancyholograms.property.item.ItemHologramItemProperty;
import me.m56738.easyarmorstands.fancyholograms.property.text.TextHologramAlignmentProperty;
import me.m56738.easyarmorstands.fancyholograms.property.text.TextHologramBackgroundProperty;
import me.m56738.easyarmorstands.fancyholograms.property.text.TextHologramSeeThroughProperty;
import me.m56738.easyarmorstands.fancyholograms.property.text.TextHologramShadowProperty;
import me.m56738.easyarmorstands.fancyholograms.property.text.TextHologramTextProperty;
import me.m56738.easyarmorstands.fancyholograms.property.type.HologramDataPropertyType;
import me.m56738.easyarmorstands.common.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HologramElementType implements ElementType {
    private final HologramManager manager;
    private final FancyHologramsAddon addon;

    public HologramElementType(HologramManager manager, FancyHologramsAddon addon) {
        this.manager = manager;
        this.addon = addon;
    }

    public HologramElement getElement(Hologram hologram) {
        HologramElement element = new HologramElement(this, manager, hologram, addon);
        HologramData data = hologram.getData();
        element.getProperties().register(new HologramDataProperty(hologram));
        element.getProperties().register(new HologramLocationProperty(hologram));
        if (data instanceof DisplayHologramData displayData) {
            element.getProperties().register(new DisplayHologramScaleProperty(hologram, displayData));
            element.getProperties().register(new DisplayHologramBillboardProperty(hologram, displayData));
        }
        if (data instanceof ItemHologramData itemData) {
            element.getProperties().register(new ItemHologramItemProperty(hologram, itemData));
        }
        if (data instanceof BlockHologramData blockData) {
            element.getProperties().register(new BlockHologramBlockProperty(hologram, blockData));
        }
        if (data instanceof TextHologramData textData) {
            element.getProperties().register(new TextHologramTextProperty(hologram, textData));
            element.getProperties().register(new TextHologramShadowProperty(hologram, textData));
            element.getProperties().register(new TextHologramSeeThroughProperty(hologram, textData));
            element.getProperties().register(new TextHologramAlignmentProperty(hologram, textData));
            element.getProperties().register(new TextHologramBackgroundProperty(hologram, textData));
        }
        return element;
    }

    @Override
    public boolean canSpawn(@NotNull Player player) {
        return player.hasPermission(Permissions.FANCYHOLOGRAMS_SPAWN);
    }

    @Override
    public @Nullable Element createElement(@NotNull PropertyContainer properties) {
        Property<HologramData> property = properties.getOrNull(HologramDataPropertyType.INSTANCE);
        if (property == null) {
            return null;
        }

        HologramData data = property.getValue();

        if (manager.getHologram(data.getName()).isPresent()) {
            return null;
        }

        Hologram hologram = manager.create(data);
        manager.addHologram(hologram);

        return getElement(hologram);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("Hologram");
    }
}
