package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
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
    public @NotNull ToolContext context() {
        return ToolContext.of(positionProvider, RotationProvider.identity());
    }

    @Override
    public @Nullable MoveTool move(@NotNull ToolContext context) {
        return new BoxMoveTool(editor, context);
    }
}
