package me.m56738.easyarmorstands.paper.api.element;

import me.m56738.easyarmorstands.api.element.ElementType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.Nullable;

public interface EntityElementType<E extends Entity> extends ElementType {
    EntityType getEntityType();

    Class<E> getEntityClass();

    // TODO nullable?
    @Nullable
    EntityElement<E> getElement(E entity);
}
