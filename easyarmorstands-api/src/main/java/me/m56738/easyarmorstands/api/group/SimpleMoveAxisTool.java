package me.m56738.easyarmorstands.api.group;

import me.m56738.easyarmorstands.api.Axis;
import org.joml.Vector3d;

@Deprecated
class SimpleMoveAxisTool implements GroupMoveAxisTool {
    private final GroupMoveTool moveTool;
    private final Axis axis;
    private final Vector3d offset = new Vector3d();

    SimpleMoveAxisTool(GroupMoveTool moveTool, Axis axis) {
        this.moveTool = moveTool;
        this.axis = axis;
    }

    @Override
    public void setOffset(double offset) {
        axis.setValue(this.offset, offset);
        moveTool.setOffset(this.offset);
    }

    @Override
    public void revert() {
        moveTool.revert();
    }

    @Override
    public void commit() {
        moveTool.commit();
    }

    @Override
    public boolean isValid() {
        return moveTool.isValid();
    }
}
