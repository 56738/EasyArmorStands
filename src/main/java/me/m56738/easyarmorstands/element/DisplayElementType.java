package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DisplayElementType<E extends Display> extends SimpleEntityElementType<E> {
    public DisplayElementType(EntityType entityType, Class<E> entityClass) {
        super(entityType, entityClass);
    }

    @Override
    protected SimpleEntityElement<E> createInstance(E entity) {
        return new DisplayElement<>(entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue().clone();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);
        if (getEntityType() == EntityType.BLOCK_DISPLAY) {
            properties.put(DisplayPropertyTypes.TRANSLATION, new Vector3f(-0.5f));
        }
    }
}
