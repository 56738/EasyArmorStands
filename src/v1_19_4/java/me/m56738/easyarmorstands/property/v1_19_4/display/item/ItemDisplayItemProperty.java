package me.m56738.easyarmorstands.property.v1_19_4.display.item;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayItemProperty implements Property<ItemStack> {
    private final ItemDisplay entity;

    public ItemDisplayItemProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ItemStack> getType() {
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
