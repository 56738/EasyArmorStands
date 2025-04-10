package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;

class ToolContextImpl implements ToolContext {
    private final @NotNull PositionProvider positionProvider;
    private final @NotNull RotationProvider rotationProvider;

    ToolContextImpl(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider) {
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
    }

    @Override
    public @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public @NotNull RotationProvider rotation() {
        return rotationProvider;
    }
}
