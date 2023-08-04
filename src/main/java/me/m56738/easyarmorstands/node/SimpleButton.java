package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.session.Session;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public abstract class SimpleButton implements Button {
    private final Session session;
    private final ParticleColor color;
    private int priority = 0;
    private Vector3dc lookTarget;
    private PointParticle particle;

    public SimpleButton(Session session, ParticleColor color) {
        this.session = session;
        this.color = color;
        this.particle = EasyArmorStands.getInstance().getCapability(ParticleCapability.class).createPoint();
    }

    protected abstract Vector3dc getPosition();

    @Override
    public int getLookPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public void update() {
    }

    @Override
    public void updateLookTarget(Vector3dc eyes, Vector3dc target) {
        Vector3dc position = getPosition();
        if (session.isLookingAtPoint(eyes, target, position)) {
            lookTarget = position;
        } else {
            lookTarget = null;
        }
    }

    @Override
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public void updatePreview(boolean focused) {
        particle.setPosition(getPosition());
        particle.setColor(focused ? ParticleColor.YELLOW : color);
    }

    @Override
    public void showPreview() {
        session.addParticle(particle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(particle);
    }
}
