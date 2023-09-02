package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class GlobalAxisMoveTool implements AxisMoveTool {
    public static final NumberFormat POSITION_FORMAT = new DecimalFormat("0.0000");
    private final MoveTool moveTool;
    private final Axis axis;

    GlobalAxisMoveTool(MoveTool moveTool, Axis axis) {
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

        public AxisMoveToolSessionImpl(MoveToolSession moveToolSession) {
            this.moveToolSession = moveToolSession;
        }

        @Override
        public void setChange(double change) {
            moveToolSession.setOffset(axis.getDirection().mul(change, new Vector3d()));
        }

        @Override
        public void setValue(double value) {
            Vector3d position = new Vector3d(moveToolSession.getPosition());
            axis.setValue(position, value);
            moveToolSession.setPosition(position);
        }

        @Override
        public void revert() {
            moveToolSession.revert();
        }

        @Override
        public void commit(@Nullable Component description) {
            moveToolSession.commit(description);
        }

        @Override
        public boolean isValid() {
            return moveToolSession.isValid();
        }

        @Override
        public @Nullable Component getStatus() {
            return Component.text(POSITION_FORMAT.format(axis.getValue(moveToolSession.getPosition())));
        }

        @Override
        public @Nullable Component getDescription() {
            return moveToolSession.getDescription();
        }
    }
}
