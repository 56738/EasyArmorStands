package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.entity.Entity;

public abstract class EntityProperty<E extends Entity, T> implements Property<T> {
    protected final E entity;

    public EntityProperty(E entity) {
        this.entity = entity;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
