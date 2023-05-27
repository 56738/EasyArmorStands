package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class AxisAlignedBoxButton implements Button {
    private final Session session;
    private final Vector3d position = new Vector3d();
    private final Vector3d center = new Vector3d();
    private final Vector3d size = new Vector3d();
    private Vector3dc lookTarget;
    private int lookPriority = 0;

    public AxisAlignedBoxButton(Session session) {
        this.session = session;
    }

    protected abstract Vector3dc getPosition();

    protected Vector3dc getCenter() {
        return getPosition();
    }

    protected abstract Vector3dc getSize();

    @Override
    public void update(Vector3dc eyes, Vector3dc target) {
        position.set(getPosition());
        center.set(getCenter());
        size.set(getSize());
        if (session.isLookingAtPoint(eyes, target, position)) {
            lookTarget = position;
            lookPriority = 0;
            return;
        }
        if (size.x != 0 && size.y != 0 && size.z != 0) {
            Vector3d min = center.fma(-0.5, size, new Vector3d());
            Vector3d max = center.fma(0.5, size, new Vector3d());
            Vector3d direction = target.sub(eyes, new Vector3d());
            Vector2d result = new Vector2d();
            if (Intersectiond.intersectRayAab(eyes, direction, min, max, result) && result.x <= 1) {
                if (result.x > 0) {
                    // Looking at the box from outside
                    lookTarget = eyes.fma(result.x, direction, new Vector3d());
                    lookPriority = -1;
                } else {
                    // Inside the box
                    lookTarget = eyes;
                    lookPriority = -2;
                }
                return;
            }
        }

        lookTarget = null;
    }

    @Override
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public int getLookPriority() {
        return lookPriority;
    }

    @Override
    public void showPreview(boolean focused) {
        session.showPoint(position, focused ? NamedTextColor.YELLOW : NamedTextColor.WHITE);
        if (focused && size.x != 0 && size.y != 0) {
            session.showAxisAlignedBox(center, size, NamedTextColor.YELLOW);
        }
    }
}
