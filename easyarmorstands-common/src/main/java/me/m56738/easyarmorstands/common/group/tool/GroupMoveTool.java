package me.m56738.easyarmorstands.common.group.tool;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.util.EasFormat;
import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class GroupMoveTool implements MoveTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final List<MoveTool> tools;

    public GroupMoveTool(ToolContext context, ChangeContext changeContext, List<MoveTool> tools) {
        this.context = context;
        this.changeContext = changeContext;
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
    public @NotNull MoveToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        for (MoveTool tool : tools) {
            if (tool.canUse(player)) {
                return true;
            }
        }
        return false;
    }

    private class SessionImpl extends GroupToolSession<MoveToolSession> implements MoveToolSession {
        private final Vector3dc originalPosition;
        private final Vector3d offset = new Vector3d();

        private SessionImpl() {
            super(tools);
            originalPosition = new Vector3d(context.position().getPosition());
        }

        @Override
        public void setChange(@NotNull Vector3dc change) {
            this.offset.set(change);
            for (MoveToolSession session : sessions) {
                session.setChange(change);
            }
        }

        @Override
        public void snapChange(@NotNull Vector3d change, @NotNull Snapper context) {
            context.snapOffset(change);
        }

        @Override
        public @NotNull Vector3dc getPosition() {
            return originalPosition.add(offset, new Vector3d());
        }

        @Override
        public void setPosition(@NotNull Vector3dc position) {
            position.sub(originalPosition, offset);
            setChange(offset);
        }

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public @Nullable Component getStatus() {
            return EasFormat.formatOffset(offset);
        }

        @Override
        public @Nullable Component getDescription() {
            Component count = Component.text(sessions.size());
            Component value = EasFormat.formatOffset(offset);
            return Message.component("easyarmorstands.history.group.move", count, value);
        }
    }
}
