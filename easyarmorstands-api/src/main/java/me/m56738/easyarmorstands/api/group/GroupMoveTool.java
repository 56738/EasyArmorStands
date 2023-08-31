package me.m56738.easyarmorstands.api.group;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public interface GroupMoveTool extends GroupTool {
    void setOffset(@NotNull Vector3dc offset);
}
