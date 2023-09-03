package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class GroupAxisMoveTool implements AxisMoveTool {
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final Axis axis;
    private final List<AxisMoveTool> tools;

    public GroupAxisMoveTool(PositionProvider positionProvider, RotationProvider rotationProvider, Axis axis, List<AxisMoveTool> tools) {
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
        this.axis = axis;
        this.tools = new ArrayList<>(tools);
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
    public @NotNull AxisMoveToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends GroupToolSession<AxisMoveToolSession> implements AxisMoveToolSession {
        private double change;

        private SessionImpl() {
            super(tools);
        }

        @Override
        public void setChange(double change) {
            this.change = change;
            for (AxisMoveToolSession session : sessions) {
                session.setChange(change);
            }
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            return context.snapOffset(change);
        }

        @Override
        public @Nullable Component getStatus() {
            return Component.text(Util.OFFSET_FORMAT.format(change));
        }

        @Override
        public @Nullable Component getDescription() {
            Component count = Component.text(sessions.size());
            Component axisName = Component.text(axis.getName(), TextColor.color(axis.getColor()));
            return Message.component("easyarmorstands.history.group.move-axis", count, axisName);
        }
    }
}
