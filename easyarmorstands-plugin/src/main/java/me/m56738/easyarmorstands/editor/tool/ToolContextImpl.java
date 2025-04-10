package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;

public class ToolContextImpl implements ToolContext {
    private final @NotNull PositionProvider positionProvider;
    private final @NotNull RotationProvider rotationProvider;

    public ToolContextImpl(
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
