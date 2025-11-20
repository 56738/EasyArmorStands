package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MannequinElementType<E extends Entity> extends SimpleEntityElementType<E> {
    public MannequinElementType(EntityType entityType, Class<E> entityClass) {
        super(entityType, entityClass);
    }

    @Override
    protected SimpleEntityElement<E> createInstance(E entity) {
        return new MannequinElement<>(entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(MannequinPropertyTypes.DESCRIPTION, Optional.empty());
        properties.put(MannequinPropertyTypes.IMMOVABLE, true);
    }

    @Override
    public boolean isSpawnedAtEyeHeight() {
        return false;
    }
}
