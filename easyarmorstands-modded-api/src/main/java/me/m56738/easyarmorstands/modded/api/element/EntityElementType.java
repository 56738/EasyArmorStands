package me.m56738.easyarmorstands.modded.api.element;

import me.m56738.easyarmorstands.api.element.ElementType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jspecify.annotations.Nullable;

public interface EntityElementType<E extends Entity> extends ElementType {
    EntityType<E> getEntityType();

    Class<E> getEntityClass();

    // TODO nullable?
    @Nullable
    EntityElement<E> getElement(E entity);
}
