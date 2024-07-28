package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoxCenterPositionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoxToolProvider implements ToolProvider {
    private final BoundingBoxEditor editor;
    private final PositionProvider positionProvider;

    public BoxToolProvider(BoundingBoxEditor editor) {
        this.editor = editor;
        this.positionProvider = new BoxCenterPositionProvider(editor);
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
        return new BoxMoveTool(editor, positionProvider, rotationProvider);
    }
}
