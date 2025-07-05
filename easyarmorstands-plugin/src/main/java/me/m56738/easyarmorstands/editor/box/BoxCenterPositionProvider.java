package me.m56738.easyarmorstands.editor.box;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.NotNull;

public class BoxCenterPositionProvider implements PositionProvider {
    private final BoundingBoxEditor editor;
    private final Vector3d center = new Vector3d();

    public BoxCenterPositionProvider(BoundingBoxEditor editor) {
        this.editor = editor;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return editor.getBoundingBox().getCenter(center);
    }
}
