package me.m56738.easyarmorstands.api.editor.context;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.Objects;

@ApiStatus.NonExtendable
public interface EnterContext {
    @Contract(pure = true)
    @Nullable Vector3dc cursor();

    @Contract(pure = true)
    default @NotNull Vector3dc cursorOrDefault(@NotNull Vector3dc defaultCursor) {
        Vector3dc result = cursor();
        if (result == null) {
            result = Objects.requireNonNull(defaultCursor);
        }
        return result;
    }
}
