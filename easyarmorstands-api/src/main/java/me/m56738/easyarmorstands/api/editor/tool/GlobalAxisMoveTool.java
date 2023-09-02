package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

class GlobalAxisMoveTool extends SimpleAxisMoveTool {
    private final MoveTool moveTool;
    private final Axis axis;

    GlobalAxisMoveTool(MoveTool moveTool, Axis axis) {
        super(moveTool, axis);
        this.moveTool = moveTool;
        this.axis = axis;
    }

    @Override
    public @Nullable Double getInitialValue() {
        Vector3dc position = moveTool.getInitialValue();
        if (position != null) {
            return axis.getValue(position);
        }
        return null;
    }
}
