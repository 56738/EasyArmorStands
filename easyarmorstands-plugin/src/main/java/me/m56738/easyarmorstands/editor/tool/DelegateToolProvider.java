package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DelegateToolProvider implements ToolProvider {
    private final ToolProvider toolProvider;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public DelegateToolProvider(ToolProvider toolProvider, PositionProvider positionProvider, RotationProvider rotationProvider) {
        this.toolProvider = toolProvider;
        if (positionProvider != null) {
            this.positionProvider = positionProvider;
        } else {
            this.positionProvider = toolProvider.position();
        }
        if (rotationProvider != null) {
            this.rotationProvider = rotationProvider;
        } else {
            this.rotationProvider = toolProvider.rotation();
        }
    }

    @Override
    public @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public @NotNull RotationProvider rotation() {
        return rotationProvider;
    }

    @Override
    @Contract(pure = true)
    public @Nullable MoveTool move(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider) {
        return toolProvider.move(positionProvider, rotationProvider);
    }

    @Override
    @Contract(pure = true)
    public @Nullable AxisMoveTool move(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        return toolProvider.move(positionProvider, rotationProvider, axis);
    }

    @Override
    @Contract(pure = true)
    public @Nullable AxisRotateTool rotate(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        return toolProvider.rotate(positionProvider, rotationProvider, axis);
    }

    @Override
    @Contract(pure = true)
    public @Nullable ScaleTool scale(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider) {
        return toolProvider.scale(positionProvider, rotationProvider);
    }

    @Override
    @Contract(pure = true)
    public @Nullable AxisScaleTool scale(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        return toolProvider.scale(positionProvider, rotationProvider, axis);
    }
}
