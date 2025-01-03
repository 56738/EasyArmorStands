package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An element which allows registering properties into it.
 *
 * @see me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent EntityElementInitializeEvent
 */
public interface ConfigurableEntityElement<E extends Entity> extends EntityElement<E> {
    @Override
    @NotNull PropertyRegistry getProperties();
}
