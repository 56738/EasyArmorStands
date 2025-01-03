package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityElementType<E extends Entity> extends ElementType {
    @Contract(pure = true)
    @NotNull EntityType getEntityType();

    @Contract(pure = true)
    @NotNull Class<E> getEntityClass();

    @Contract(pure = true)
    @Nullable EntityElement<E> getElement(@NotNull E entity);

    @Override
    @Nullable EntityElement<E> createElement(@NotNull PropertyContainer properties);
}
