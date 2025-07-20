package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;

class SimpleAxisMoveTool implements AxisMoveTool {
    public static final NumberFormat OFFSET_FORMAT = new DecimalFormat("+0.0000;-0.0000");
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

    @Override
    public boolean canUse(@NotNull Player player) {
        return moveTool.canUse(player);
    }

    private class AxisMoveToolSessionImpl implements AxisMoveToolSession {
        private final MoveToolSession moveToolSession;
        private final Vector3dc direction;
        private double change;

        public AxisMoveToolSessionImpl(MoveToolSession moveToolSession) {
            this.moveToolSession = moveToolSession;
            this.direction = axis.getDirection().rotate(getRotation(), new Vector3d());
        }

        @Override
        public void setChange(double change) {
            this.change = change;
            moveToolSession.setChange(direction.mul(change, new Vector3d()));
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            return context.snapOffset(change);
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
            return Component.text(OFFSET_FORMAT.format(change));
        }

        @Override
        public @Nullable Component getDescription() {
            return moveToolSession.getDescription();
        }

        @Override
        public boolean canSetValue(Player player) {
            return moveToolSession.canSetPosition(player);
        }
    }
}
