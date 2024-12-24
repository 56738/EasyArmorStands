package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EntityElement<E extends Entity> extends Element {
    @Contract(pure = true)
    @NotNull E getEntity();

    @Override
    @NotNull EntityElementType<E> getType();

    @Override
    default @NotNull EntityElementReference<E> getReference() {
        return EntityElementReference.of(getType(), getEntity());
    }
}
