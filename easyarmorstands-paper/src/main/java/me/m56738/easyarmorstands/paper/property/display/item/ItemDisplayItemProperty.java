package me.m56738.easyarmorstands.paper.property.display.item;

import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayItemProperty implements Property<Item> {
    private final ItemDisplay entity;

    public ItemDisplayItemProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Item> getType() {
        return ItemDisplayPropertyTypes.ITEM;
    }

    @Override
    public Item getValue() {
        return PaperItem.fromNative(entity.getItemStack());
    }

    @Override
    public boolean setValue(Item value) {
        entity.setItemStack(PaperItem.toNative(value));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
