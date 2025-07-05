package me.m56738.easyarmorstands.api.editor.button;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface EditorButtonProvider {
    @Contract(pure = true)
    @NotNull MoveButtonBuilder move();

    @Contract(pure = true)
    @NotNull AxisMoveButtonBuilder axisMove();

    @Contract(pure = true)
    @NotNull ScaleButtonBuilder scale();

    @Contract(pure = true)
    @NotNull AxisScaleButtonBuilder axisScale();

    @Contract(pure = true)
    @NotNull AxisRotateButtonBuilder axisRotate();
}
