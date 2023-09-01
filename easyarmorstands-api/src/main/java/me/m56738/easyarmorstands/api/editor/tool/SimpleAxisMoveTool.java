package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

class SimpleAxisMoveTool implements AxisMoveTool {
    private final MoveTool moveTool;
    private final Axis axis;

    SimpleAxisMoveTool(MoveTool moveTool, Axis axis) {
        this.moveTool = moveTool;
        this.axis = axis;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return moveTool.getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return moveTool.getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public @NotNull AxisMoveToolSession start() {
        return new AxisMoveToolSessionImpl(moveTool.start());
    }

    private class AxisMoveToolSessionImpl implements AxisMoveToolSession {
        private final MoveToolSession moveToolSession;
        private final Vector3dc direction;

        public AxisMoveToolSessionImpl(MoveToolSession moveToolSession) {
            this.moveToolSession = moveToolSession;
            this.direction = axis.getDirection().rotate(getRotation(), new Vector3d());
        }

        @Override
        public void setChange(double change) {
            moveToolSession.setOffset(direction.mul(change, new Vector3d()));
        }

        @Override
        public void revert() {
            moveToolSession.revert();
        }

        @Override
        public void commit() {
            moveToolSession.commit();
        }

        @Override
        public boolean isValid() {
            return moveToolSession.isValid();
        }
    }
}
