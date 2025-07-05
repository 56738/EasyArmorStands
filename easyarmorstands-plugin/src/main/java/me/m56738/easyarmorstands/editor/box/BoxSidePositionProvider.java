package me.m56738.easyarmorstands.editor.box;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoxSidePositionProvider implements PositionProvider {
    private final BoundingBoxEditor editor;
    private final Vector3dc offset;
    private final Vector3d center = new Vector3d();
    private final Vector3d size = new Vector3d();

    public BoxSidePositionProvider(BoundingBoxEditor editor, Axis axis, boolean end) {
        this(editor, getOffset(axis, end));
    }

    public BoxSidePositionProvider(BoundingBoxEditor editor, Vector3dc offset) {
        this.editor = editor;
        this.offset = new Vector3d(offset);
    }

    private static Vector3dc getOffset(Axis axis, boolean end) {
        Vector3d offset = new Vector3d(axis.getDirection());
        if (end) {
            // positive end
            offset.mul(0.5);
        } else {
            // negative end
            offset.mul(-0.5);
        }
        return offset;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        editor.getBoundingBox().getCenter(center);
        editor.getBoundingBox().getSize(size);
        return offset.mul(size, new Vector3d()).add(center);
    }
}
