package me.m56738.easyarmorstands.display.property.display.item;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.ItemDisplayPropertyTypes;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.jetbrains.annotations.NotNull;

public class ItemDisplayTransformProperty implements Property<ItemDisplayTransform> {
    private final ItemDisplay entity;

    public ItemDisplayTransformProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<ItemDisplayTransform> getType() {
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
}
