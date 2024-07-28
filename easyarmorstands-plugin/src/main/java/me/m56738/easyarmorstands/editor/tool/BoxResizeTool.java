package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditorSession;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoxResizeTool implements AxisMoveTool {
    private final BoundingBoxEditor editor;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final Axis axis;
    private final boolean end;

    public BoxResizeTool(BoundingBoxEditor editor, PositionProvider positionProvider, RotationProvider rotationProvider, Axis axis, boolean end) {
        this.editor = editor;
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
        this.axis = axis;
        this.end = end;
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
    public boolean canUse(@NotNull Player player) {
        return editor.canResize(player);
    }

    @Override
    public @NotNull AxisMoveToolSession start() {
        return new SessionImpl(editor.start());
    }

    private class SessionImpl implements AxisMoveToolSession {
        private final BoundingBoxEditorSession session;
        private final Vector3dc originalCenter;
        private final float originalWidth;
        private final float originalHeight;
        private final float originalValue;

        public SessionImpl(BoundingBoxEditorSession session) {
            this.session = session;
            this.originalCenter = session.getBoundingBox().getCenter(new Vector3d());
            this.originalWidth = session.getWidth();
            this.originalHeight = session.getHeight();
            if (axis == Axis.Y) {
                this.originalValue = this.originalHeight;
            } else {
                this.originalValue = this.originalWidth;
            }
        }

        @Override
        public void setChange(double change) {
            if (!end) {
                change = -change;
            }

            if (axis == Axis.Y) {
                double offset = change / 2;
                if (!end) {
                    offset = -offset;
                }
                float height = originalHeight + (float) change;
                Vector3dc center = originalCenter.add(0, offset, 0, new Vector3d());
                if (session.setHeight(Math.abs(height))) {
                    session.setCenter(center);
                }
            } else {
                Vector3d offset = new Vector3d();
                axis.setValue(offset, change / 2);
                if (!end) {
                    offset.negate();
                }
                float width = originalWidth + (float) change;
                Vector3dc center = originalCenter.add(offset, new Vector3d());
                if (session.setWidth(Math.abs(width))) {
                    session.setCenter(center);
                }
            }
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            if (!end) {
                change = -change;
            }
            change += originalValue;
            change = context.snapPosition(change);
            change -= originalValue;
            if (!end) {
                change = -change;
            }
            return change;
        }

        @Override
        public void setValue(double value) {
            double change = value - originalValue;
            if (!end) {
                change = -change;
            }
            setChange(change);
        }

        @Override
        public void revert() {
            session.revert();
        }

        @Override
        public void commit(@Nullable Component description) {
            session.commit(description);
        }

        @Override
        public boolean isValid() {
            return session.isValid();
        }

        @Override
        public @Nullable Component getStatus() {
            float value;
            if (axis == Axis.Y) {
                value = session.getHeight();
            } else {
                value = session.getWidth();
            }
            return Component.text(Util.SCALE_FORMAT.format(value));
        }

        @Override
        public @Nullable Component getDescription() {
            return Message.component("easyarmorstands.history.resize-box");
        }
    }
}
