package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.Nullable;

public interface AxisMoveTool extends AxisTool<AxisMoveToolSession> {
    default @Nullable Double getInitialValue() {
        return null;
    }

    default boolean isInverted() {
        return false;
    }
}
