package me.m56738.easyarmorstands.group.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.group.GroupMoveAxisTool;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.Collection;

public class GroupMoveAxis extends GroupAxis<GroupMoveAxisTool> implements MoveAxis {
    private final Group group;
    private final Axis axis;

    public GroupMoveAxis(Group group, Axis axis, Collection<GroupMoveAxisTool> tools) {
        super(tools);
        this.group = group;
        this.axis = axis;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return group.getAveragePosition();
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
        for (GroupMoveAxisTool tool : getTools()) {
            tool.setOffset(value);
        }
    }
}
