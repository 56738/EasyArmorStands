package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.platform.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An element which represents an entity.
 */
public interface EntityElement<E extends Entity> extends Element {
    /**
     * Returns the entity represented by this element.
     *
     * @return the entity
     */
    @Contract(pure = true)
    @NotNull E getEntity();

    @Override
    @NotNull EntityElementType<E> getType();

    @Override
    default @NotNull EntityElementReference<E> getReference(ReferenceProvider provider) {
        return provider.createEntityElementReference(getType(), getEntity());
    }
}
