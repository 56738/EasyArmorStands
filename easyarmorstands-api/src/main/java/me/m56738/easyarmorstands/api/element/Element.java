package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Element {
    @Contract(pure = true)
    @NotNull ElementType getType();

    @Contract(pure = true)
    @NotNull PropertyContainer getProperties();

    @Contract(pure = true)
    @NotNull ElementReference getReference();

    @Contract(pure = true)
    boolean isValid();
}
