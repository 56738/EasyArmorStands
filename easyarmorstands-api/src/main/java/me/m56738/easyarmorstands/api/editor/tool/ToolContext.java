package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;

public interface ToolContext {
    static @NotNull ToolContext of(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider) {
        return new ToolContextImpl(positionProvider, rotationProvider);
    }

    @NotNull PositionProvider position();

    @NotNull RotationProvider rotation();
}
