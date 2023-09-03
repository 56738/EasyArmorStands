package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface EntityElementType<E extends Entity> extends ElementType {
    EntityType getEntityType();

    Class<E> getEntityClass();

    EntityElement<E> getElement(E entity);

    @Override
    EntityElement<E> createElement(PropertyContainer properties);
}
