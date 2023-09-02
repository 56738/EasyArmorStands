package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface MoveTool extends PositionedTool<MoveToolSession>, OrientedTool<MoveToolSession> {
    default @Nullable Vector3dc getInitialValue() {
        return null;
    }
}
