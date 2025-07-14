package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.history.EntityReplacementListener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Stable reference which can find an element even after it was deleted and recreated or
 * after the chunk was unloaded and loaded.
 */
public interface ElementReference extends EntityReplacementListener {
    /**
     * Returns the type of the element.
     *
     * @return the type
     */
    @Contract(pure = true)
    @NotNull ElementType getType();

    /**
     * Attempts to find the element.
     * <p>
     * Usually creates a new element instance representing the same element.
     *
     * @return the element or {@code null}
     */
    @Contract(pure = true)
    @Nullable Element getElement();

    @Override
    default void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
    }
}
