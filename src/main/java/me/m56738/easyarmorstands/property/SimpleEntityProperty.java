package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

public abstract class SimpleEntityProperty<T> implements Property<T> {
    private final EntityPropertyType<T> type;
    private final Entity entity;

    protected SimpleEntityProperty(EntityPropertyType<T> type, Entity entity) {
        this.type = type;
        this.entity = entity;
    }

    @Override
    public final PropertyType<T> getType() {
        return type;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public final PropertyReference<T> asReference() {
        return new EntityPropertyReference<>(entity.getUniqueId(), type);
    }
}
