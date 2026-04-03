package me.m56738.easyarmorstands.api.editor.button;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface ButtonResult {
    @Contract(value = "_ -> new", pure = true)
    static @NotNull ButtonResult of(@NotNull Vector3dc position) {
        return ButtonResult.of(position, 0);
    }

    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull ButtonResult of(@NotNull Vector3dc position, int priority) {
        return new ButtonResultImpl(position, priority);
    }

    /**
     * Returns the location where the eye ray intersects this button.
     *
     * @return Position of the intersection with the eye ray, or null if there is none.
     */
    @Contract(pure = true)
    @NotNull Vector3dc position();

    /**
     * Returns the priority of this result.
     * If the player is looking at multiple buttons, the result with the highest priority wins.
     *
     * @return The priority.
     */
    @Contract(pure = true)
    int priority();
}
