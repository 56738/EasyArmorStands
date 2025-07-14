package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ToolProvider {
    @Contract(pure = true)
    @NotNull
    ToolContext context();

    default @Nullable MoveTool move(@NotNull ToolContext context) {
        return null;
    }

    @Contract(pure = true)
    default @Nullable AxisMoveTool move(@NotNull ToolContext context, @NotNull Axis axis) {
        MoveTool moveTool = move(context);
        if (moveTool != null) {
            return new SimpleAxisMoveTool(moveTool, axis);
        }
        return null;
    }

    @Contract(pure = true)
    default @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        return null;
    }

    @Contract(pure = true)
    default @Nullable ScaleTool scale(@NotNull ToolContext context) {
        return null;
    }

    @Contract(pure = true)
    default @Nullable AxisScaleTool scale(@NotNull ToolContext context, @NotNull Axis axis) {
        return null;
    }
}
