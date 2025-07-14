package me.m56738.easyarmorstands.paper.property.display.item;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class ItemDisplayItemProperty implements Property<ItemStack> {
    private final ItemDisplay entity;

    public ItemDisplayItemProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ItemStack> getType() {
        return ItemDisplayPropertyTypes.ITEM;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
