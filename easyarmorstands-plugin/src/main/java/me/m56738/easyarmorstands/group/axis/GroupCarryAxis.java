package me.m56738.easyarmorstands.group.axis;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.group.GroupMoveTool;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Collection;

public class GroupCarryAxis extends GroupAxis<GroupMoveTool> implements CarryAxis {
    private final Group group;
    private final Vector3d originalPosition = new Vector3d();
    private final Vector3d relativePosition = new Vector3d();
    private final Vector3d position = new Vector3d();
    private final Vector3d offset = new Vector3d();

    public GroupCarryAxis(Group group, Collection<GroupMoveTool> tools) {
        super(tools);
        this.group = group;
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
    public void start(@NotNull EyeRay eyeRay) {
        originalPosition.set(getPosition());
        eyeRay.inverseMatrix().transformPosition(originalPosition, relativePosition);
    }

    @Override
    public void update(@NotNull EyeRay eyeRay) {
        eyeRay.matrix().transformPosition(relativePosition, position);
        position.sub(originalPosition, offset);
        for (GroupMoveTool tool : getTools()) {
            tool.setOffset(offset);
        }
    }
}
