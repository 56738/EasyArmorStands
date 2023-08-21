package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Entity;

import java.util.UUID;

public interface EntityElementReference<E extends Entity> extends ElementReference {
    @Override
    EntityElementType<E> getType();

    @Override
    EntityElement<E> getElement();

    UUID getId();
}
