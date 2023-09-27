package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface EntityElementReference<E extends Entity> extends ElementReference {
    @Override
    @NotNull EntityElementType<E> getType();

    @Override
    @Nullable Element getElement();

    @Contract(pure = true)
    @NotNull UUID getId();
}
