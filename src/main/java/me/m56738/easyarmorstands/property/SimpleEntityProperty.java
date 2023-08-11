package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

@Deprecated
public abstract class SimpleEntityProperty<T> implements Property<T> {
    private final EntityPropertyType<T> type;
    private final Entity entity;

    @SuppressWarnings("PatternValidation")
    protected SimpleEntityProperty(EntityPropertyType<T> type, Entity entity) {
        this.type = type;
        this.entity = entity;
    }

    @Override
    public PropertyType<T> getType() {
        return type;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
