package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.session.Session;
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
    private final PointParticle pointParticle;
    private final AxisAlignedBoxParticle boxParticle;
    private boolean previewVisible;
    private boolean boxVisible;
    private Vector3dc lookTarget;
    private int lookPriority = 0;

    public AxisAlignedBoxButton(Session session) {
        this.session = session;
        ParticleCapability particleCapability = EasyArmorStands.getInstance().getCapability(ParticleCapability.class);
        this.pointParticle = particleCapability.createPoint();
        this.boxParticle = particleCapability.createAxisAlignedBox();
        this.boxParticle.setLineWidth(0.03125);
    }

    protected abstract Vector3dc getPosition();

    protected Vector3dc getCenter() {
        return getPosition();
    }

    protected abstract Vector3dc getSize();

    @Override
    public void update() {
        position.set(getPosition());
        center.set(getCenter());
        size.set(getSize());
    }

    @Override
    public void updateLookTarget(Vector3dc eyes, Vector3dc target) {
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
    public void updatePreview(boolean focused) {
        pointParticle.setPosition(position);
        pointParticle.setColor(focused ? ParticleColor.YELLOW : ParticleColor.WHITE);
        boxParticle.setCenter(center);
        boxParticle.setSize(size);
        boolean showBox = focused && size.x != 0 && size.y != 0;
        if (previewVisible && showBox != boxVisible) {
            if (showBox) {
                session.addParticle(boxParticle);
            } else {
                session.removeParticle(boxParticle);
            }
        }
        boxVisible = showBox;
    }

    @Override
    public void showPreview() {
        previewVisible = true;
        session.addParticle(pointParticle);
        if (boxVisible) {
            session.addParticle(boxParticle);
        }
    }

    @Override
    public void hidePreview() {
        previewVisible = false;
        session.removeParticle(pointParticle);
        if (boxVisible) {
            session.removeParticle(boxParticle);
        }
    }
}
