package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveToolSession;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class GroupMoveTool implements MoveTool {
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final List<MoveTool> tools;

    public GroupMoveTool(PositionProvider positionProvider, RotationProvider rotationProvider, List<MoveTool> tools) {
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
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
    public @NotNull MoveToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends GroupToolSession<MoveToolSession> implements MoveToolSession {
        private SessionImpl() {
            super(tools);
        }

        @Override
        public void setOffset(@NotNull Vector3dc offset) {
            for (MoveToolSession session : sessions) {
                session.setOffset(offset);
            }
        }
    }
}
