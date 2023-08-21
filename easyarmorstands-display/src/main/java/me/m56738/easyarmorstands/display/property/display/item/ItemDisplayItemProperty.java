package me.m56738.easyarmorstands.display.property.display.item;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
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
        return DisplayPropertyTypes.ITEM_DISPLAY_ITEM;
    }

    @Override
    public ItemStack getValue() {
        return entity.getItemStack();
    }

    @Override
    public boolean setValue(ItemStack value) {
        entity.setItemStack(value);
        return true;
    }
}
