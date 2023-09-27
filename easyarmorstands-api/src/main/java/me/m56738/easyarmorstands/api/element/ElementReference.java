package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.history.EntityReplacementListener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ElementReference extends EntityReplacementListener {
    @Contract(pure = true)
    @NotNull ElementType getType();

    @Contract(pure = true)
    @Nullable Element getElement();

    @Override
    default void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
    }
}
