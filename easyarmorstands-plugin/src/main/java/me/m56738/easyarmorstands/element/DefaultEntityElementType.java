package me.m56738.easyarmorstands.element;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class DefaultEntityElementType<E extends Entity> extends SimpleEntityElementType<E> {
    public DefaultEntityElementType(EntityType entityType, Class<E> entityClass) {
        super(entityType, entityClass);
    }
}
