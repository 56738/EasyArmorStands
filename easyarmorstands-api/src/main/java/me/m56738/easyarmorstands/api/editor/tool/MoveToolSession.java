package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public interface MoveToolSession extends ToolSession {
    void setOffset(@NotNull Vector3dc offset);
}
