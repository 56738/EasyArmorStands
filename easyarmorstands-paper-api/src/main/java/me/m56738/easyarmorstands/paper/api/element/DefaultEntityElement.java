package me.m56738.easyarmorstands.paper.api.element;

import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface DefaultEntityElement<E extends Entity> extends EntityElement<E> {
    @Override
    @NotNull PropertyRegistry getProperties();
}
