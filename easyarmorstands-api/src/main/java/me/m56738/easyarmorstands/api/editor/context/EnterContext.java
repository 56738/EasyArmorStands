package me.m56738.easyarmorstands.api.editor.context;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.lib.joml.Vector2dc;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface EnterContext {
    @Contract(pure = true)
    @NotNull EyeRay eyeRay();

    @Contract(pure = true)
    @NotNull EyeRay eyeRay(@NotNull Vector2dc cursor);

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

    @Contract(pure = true)
    default @NotNull Vector3dc cursorOrDefault(@NotNull Supplier<@NotNull Vector3dc> defaultCursorSupplier) {
        Vector3dc result = cursor();
        if (result == null) {
            result = Objects.requireNonNull(defaultCursorSupplier.get());
        }
        return result;
    }
}
