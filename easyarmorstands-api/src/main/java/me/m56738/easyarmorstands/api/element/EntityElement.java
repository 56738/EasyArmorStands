package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface EntityElement<E extends Entity> extends Element {
    E getEntity();

    @Override
    @NotNull
    EntityElementType<E> getType();

    @Override
    @NotNull
    EntityElementReference<E> getReference();
}
