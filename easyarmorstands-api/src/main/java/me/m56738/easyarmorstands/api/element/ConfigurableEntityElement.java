package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface ConfigurableEntityElement<E extends Entity> extends EntityElement<E> {
    @Override
    @NotNull PropertyRegistry getProperties();
}
