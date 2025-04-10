package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;

public interface ToolContext {
    @NotNull PositionProvider position();

    @NotNull RotationProvider rotation();
}
