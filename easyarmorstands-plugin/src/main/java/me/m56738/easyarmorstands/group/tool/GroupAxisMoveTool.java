package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.lib.joml.Quaterniondc;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.TextColor;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GroupAxisMoveTool implements AxisMoveTool {
    private final ToolContext context;
    private final Axis axis;
    private final List<AxisMoveTool> tools;

    public GroupAxisMoveTool(ToolContext context, Axis axis, List<AxisMoveTool> tools) {
        this.context = context;
        this.axis = axis;
        this.tools = new ArrayList<>(tools);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return context.position().getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return context.rotation().getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public @NotNull AxisMoveToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        for (AxisMoveTool tool : tools) {
            if (tool.canUse(player)) {
                return true;
            }
        }
        return false;
    }

    private class SessionImpl extends GroupScalarToolSession<AxisMoveToolSession> implements AxisMoveToolSession {
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
