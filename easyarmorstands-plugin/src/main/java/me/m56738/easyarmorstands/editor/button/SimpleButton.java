package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public abstract class SimpleButton implements Button {
    private final PointParticle particle;
    private final Session session;
    private final ParticleColor color;
    private int priority = 0;

    public SimpleButton(Session session, ParticleColor color) {
        this.session = session;
        this.color = color;
        this.particle = session.particleProvider().createPoint();
        this.particle.setSize(Util.PIXEL);
    }

    protected abstract Vector3dc getPosition();

    protected Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    protected boolean isBillboard() {
        return true;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public void update() {
    }

    @Override
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectPoint(getPosition());
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection, priority));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        particle.setPosition(getPosition());
        particle.setRotation(getRotation());
        particle.setBillboard(isBillboard());
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
