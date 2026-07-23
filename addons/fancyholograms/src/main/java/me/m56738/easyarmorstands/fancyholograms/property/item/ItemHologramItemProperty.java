package me.m56738.easyarmorstands.fancyholograms.property.item;

import de.oliver.fancyholograms.api.data.ItemHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemHologramItemProperty implements Property<ItemStack> {
    private final Hologram hologram;
    private final ItemHologramData data;

    public ItemHologramItemProperty(Hologram hologram, ItemHologramData data) {
        this.hologram = hologram;
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<ItemStack> getType() {
        return ItemDisplayPropertyTypes.ITEM;
    }

    @Override
    public @NotNull ItemStack getValue() {
        return PaperItemStack.fromNative(data.getItemStack());
    }

    @Override
    public boolean setValue(@NotNull ItemStack value) {
        data.setItemStack(PaperItemStack.toNative(value));
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
