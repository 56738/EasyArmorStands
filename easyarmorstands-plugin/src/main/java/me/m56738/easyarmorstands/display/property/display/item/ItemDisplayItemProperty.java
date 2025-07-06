package me.m56738.easyarmorstands.display.property.display.item;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemDisplayItemProperty implements Property<ItemStack> {
    private final ItemDisplay entity;

    public ItemDisplayItemProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<ItemStack> getType() {
        return ItemDisplayPropertyTypes.ITEM;
    }

    @Override
    public @NotNull ItemStack getValue() {
        return Util.wrapItem(entity.getItemStack());
    }

    @Override
    public boolean setValue(@NotNull ItemStack value) {
        entity.setItemStack(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
