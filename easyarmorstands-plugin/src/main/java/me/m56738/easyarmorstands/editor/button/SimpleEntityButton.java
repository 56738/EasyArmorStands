package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class SimpleEntityButton extends AxisAlignedBoxButton {
    private final EditableElement element;

    public SimpleEntityButton(Session session, EditableElement element) {
        super(session);
        this.element = element;
    }

    private Vector3d getCenter(BoundingBox box) {
        Vector3dc min = box.getMinPosition();
        Vector3dc max = box.getMaxPosition();
        return min.lerp(max, 0.5, new Vector3d());
    }

    @Override
    protected Vector3dc getPosition() {
        BoundingBox box = element.getBoundingBox();
        Vector3d position = getCenter(box);
        position.y = box.getMinPosition().y();
        return position;
    }

    @Override
    protected Vector3dc getCenter() {
        BoundingBox box = element.getBoundingBox();
        return getCenter(box);
    }

    @Override
    protected Vector3dc getSize() {
        BoundingBox box = element.getBoundingBox();
        Vector3dc min = box.getMinPosition();
        Vector3dc max = box.getMaxPosition();
        return max.sub(min, new Vector3d());
    }
}
