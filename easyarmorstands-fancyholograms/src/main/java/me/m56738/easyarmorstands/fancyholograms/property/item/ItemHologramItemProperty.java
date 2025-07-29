package me.m56738.easyarmorstands.fancyholograms.property.item;

import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.jetbrains.annotations.NotNull;

public class ItemHologramItemProperty extends HologramProperty<Item> {
    private final ItemHologramData data;

    public ItemHologramItemProperty(Hologram hologram, ItemHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Item> getType() {
        return ItemDisplayPropertyTypes.ITEM;
    }

    @Override
    public @NotNull Item getValue() {
        return PaperItem.fromNative(data.getItemStack());
    }

    @Override
    public boolean setValue(@NotNull Item value) {
        data.setItemStack(PaperItem.toNative(value));
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
