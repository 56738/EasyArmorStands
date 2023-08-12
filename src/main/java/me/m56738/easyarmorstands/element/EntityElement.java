package me.m56738.easyarmorstands.element;

import org.bukkit.entity.Entity;

public interface EntityElement<E extends Entity> extends Element {
    E getEntity();

    @Override
    EntityElementType<E> getType();

    @Override
    EntityElementReference<E> getReference();
}
