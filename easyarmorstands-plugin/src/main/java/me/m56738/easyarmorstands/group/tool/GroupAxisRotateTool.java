package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.List;

public class GroupAxisRotateTool implements AxisRotateTool {
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final Axis axis;
    private final List<AxisRotateTool> tools;

    public GroupAxisRotateTool(PositionProvider positionProvider, RotationProvider rotationProvider, Axis axis, List<AxisRotateTool> tools) {
        this.axis = axis;
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
        this.tools = tools;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return positionProvider.getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotationProvider.getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public @NotNull AxisRotateToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends GroupToolSession<AxisRotateToolSession> implements AxisRotateToolSession {
        private SessionImpl() {
            super(tools);
        }

        @Override
        public void setAngle(double angle) {
            for (AxisRotateToolSession session : sessions) {
                session.setAngle(angle);
            }
        }
    }
}
