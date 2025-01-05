package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ToolProvider {
    @Contract(pure = true)
    @NotNull
    PositionProvider position();

    @Contract(pure = true)
    @NotNull
    RotationProvider rotation();

    @Contract(pure = true)
    default @Nullable MoveTool move(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider) {
        return null;
    }

    @Contract(pure = true)
    default @Nullable AxisMoveTool move(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider,
            @NotNull Axis axis) {
        MoveTool moveTool = move(positionProvider, rotationProvider);
        if (moveTool != null) {
            return new SimpleAxisMoveTool(moveTool, axis);
        }
        return null;
    }

    @Contract(pure = true)
    default @Nullable AxisRotateTool rotate(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider,
            @NotNull Axis axis) {
        return null;
    }

    @Contract(pure = true)
    default @Nullable ScaleTool scale(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider) {
        return null;
    }

    @Contract(pure = true)
    default @Nullable AxisScaleTool scale(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider,
            @NotNull Axis axis) {
        return null;
    }
}
