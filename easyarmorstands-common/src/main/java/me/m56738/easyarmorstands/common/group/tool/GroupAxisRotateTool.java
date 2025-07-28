package me.m56738.easyarmorstands.common.group.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.util.EasFormat;
import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.List;

public class GroupAxisRotateTool implements AxisRotateTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Axis axis;
    private final List<AxisRotateTool> tools;

    public GroupAxisRotateTool(ToolContext context, ChangeContext changeContext, Axis axis, List<AxisRotateTool> tools) {
        this.context = context;
        this.changeContext = changeContext;
        this.axis = axis;
        this.tools = tools;
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
    public @NotNull AxisRotateToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        for (AxisRotateTool tool : tools) {
            if (tool.canUse(player)) {
                return true;
            }
        }
        return false;
    }

    private class SessionImpl extends GroupScalarToolSession<AxisRotateToolSession> implements AxisRotateToolSession {
        private double change;

        private SessionImpl() {
            super(tools);
        }

        @Override
        public void setChange(double change) {
            this.change = change;
            for (AxisRotateToolSession session : sessions) {
                session.setChange(change);
            }
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            return context.snapAngle(change);
        }

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public @Nullable Component getStatus() {
            return EasFormat.formatDegrees(change);
        }

        @Override
        public @Nullable Component getDescription() {
            Component count = Component.text(sessions.size());
            Component axisName = Component.text(axis.getName(), TextColor.color(axis.getColor()));
            return Message.component("easyarmorstands.history.group.rotate-axis", count, axisName);
        }
    }
}
