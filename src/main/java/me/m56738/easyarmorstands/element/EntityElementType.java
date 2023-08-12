package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.property.PropertyContainer;
import org.bukkit.entity.Entity;

public interface EntityElementType<E extends Entity> extends ElementType {
    Class<E> getEntityType();

    EntityElement<E> getElement(E entity);

    @Override
    EntityElement<E> createElement(PropertyContainer properties);
}
