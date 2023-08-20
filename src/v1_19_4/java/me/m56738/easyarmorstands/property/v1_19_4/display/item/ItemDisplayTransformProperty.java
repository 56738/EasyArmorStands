package me.m56738.easyarmorstands.property.v1_19_4.display.item;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;

public class ItemDisplayTransformProperty implements Property<ItemDisplayTransform> {
    private final ItemDisplay entity;

    public ItemDisplayTransformProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ItemDisplayTransform> getType() {
        return DisplayPropertyTypes.ITEM_DISPLAY_TRANSFORM;
    }

    @Override
    public ItemDisplayTransform getValue() {
        return entity.getItemDisplayTransform();
    }

    @Override
    public boolean setValue(ItemDisplayTransform value) {
        entity.setItemDisplayTransform(value);
        return true;
    }
}
