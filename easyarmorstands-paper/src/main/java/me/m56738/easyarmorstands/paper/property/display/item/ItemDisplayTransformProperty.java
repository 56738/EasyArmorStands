package me.m56738.easyarmorstands.paper.property.display.item;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;

public class ItemDisplayTransformProperty implements Property<ItemDisplayTransform> {
    private final ItemDisplay entity;

    public ItemDisplayTransformProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ItemDisplayTransform> getType() {
        return ItemDisplayPropertyTypes.TRANSFORM;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
