package me.m56738.easyarmorstands.common.editor.box;

import me.m56738.easyarmorstands.api.util.PositionProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

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
