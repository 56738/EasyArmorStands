package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.property.PropertyRegistry;
import org.bukkit.entity.Entity;

public interface ConfigurableEntityElement<E extends Entity> extends EntityElement<E> {
    @Override
    PropertyRegistry getProperties();
}
