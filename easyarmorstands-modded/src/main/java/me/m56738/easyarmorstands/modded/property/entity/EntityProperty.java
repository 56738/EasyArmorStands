package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import net.minecraft.world.entity.Entity;

public abstract class EntityProperty<E extends Entity, T> implements Property<T> {
    protected final E entity;

    public EntityProperty(E entity) {
        this.entity = entity;
    }

    @Override
    public boolean isValid() {
        return entity.isAlive();
    }
}
