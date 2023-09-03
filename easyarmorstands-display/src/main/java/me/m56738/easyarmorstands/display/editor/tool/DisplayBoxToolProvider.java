package me.m56738.easyarmorstands.display.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayBoxToolProvider implements ToolProvider {
    private final PropertyContainer properties;
    private final PositionProvider positionProvider;

    public DisplayBoxToolProvider(PropertyContainer properties, PositionProvider positionProvider) {
        this.properties = properties;
        this.positionProvider = positionProvider;
    }

    @Override
    public @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public @NotNull RotationProvider rotation() {
        return RotationProvider.identity();
    }

    @Override
    public @Nullable MoveTool move(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider) {
        return new DisplayBoxMoveTool(properties, positionProvider, rotationProvider);
    }
}
