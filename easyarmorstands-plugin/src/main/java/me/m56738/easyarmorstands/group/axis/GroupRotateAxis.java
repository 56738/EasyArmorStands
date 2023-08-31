package me.m56738.easyarmorstands.group.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.group.GroupRotateTool;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.Collection;

public class GroupRotateAxis extends GroupAxis<GroupRotateTool> implements RotateAxis {
    private final Vector3dc anchor;
    private final Axis axis;

    public GroupRotateAxis(Vector3dc anchor, Axis axis, Collection<GroupRotateTool> tools) {
        super(tools);
        this.anchor = anchor;
        this.axis = axis;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return anchor;
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public double start() {
        return 0;
    }

    @Override
    public void set(double value) {
        for (GroupRotateTool tool : getTools()) {
            tool.setAngle(value);
        }
    }
}
