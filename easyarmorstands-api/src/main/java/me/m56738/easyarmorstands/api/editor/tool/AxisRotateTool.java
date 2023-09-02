package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.Nullable;

public interface AxisRotateTool extends AxisTool<AxisRotateToolSession> {
    default @Nullable Double getInitialValue() {
        return null;
    }

    default boolean isInverted() {
        return false;
    }
}
