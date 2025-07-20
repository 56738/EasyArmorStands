package me.m56738.easyarmorstands.modded.element;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.modded.api.element.EntityElement;
import me.m56738.easyarmorstands.modded.api.element.EntityElementType;
import net.minecraft.world.entity.Entity;

public abstract class AbstractEntityElement<E extends Entity> implements EntityElement<E> {
    private final EntityElementType<E> type;
    private final E entity;
    private final PropertyRegistry properties = new PropertyRegistry();

    protected AbstractEntityElement(EntityElementType<E> type, E entity) {
        this.type = type;
        this.entity = entity;
    }

    @Override
    public EntityElementType<E> getType() {
        return type;
    }

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public PropertyRegistry getProperties() {
        return properties;
    }

    @Override
    public EntityElementReference<E> getReference() {
        return new EntityElementReference<>(type, entity);
    }

    @Override
    public boolean isValid() {
        return entity.isAlive();
    }
}
