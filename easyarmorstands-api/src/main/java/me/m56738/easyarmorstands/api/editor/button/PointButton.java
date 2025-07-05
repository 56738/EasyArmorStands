package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Objects;
import java.util.function.Consumer;

public final class PointButton implements Button {
    private final PointParticle particle;
    private final Session session;
    private final PositionProvider positionProvider;
    private final @Nullable RotationProvider rotationProvider;
    private final Vector3d position = new Vector3d();
    private double scale;
    private ParticleColor color = ParticleColor.WHITE;
    private int priority = 0;

    public PointButton(@NotNull Session session, @NotNull PositionProvider positionProvider, @Nullable RotationProvider rotationProvider) {
        this.session = session;
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
        this.particle = session.particles().createPoint();
    }

    public @NotNull ParticleColor getColor() {
        return color;
    }

    public void setColor(@NotNull ParticleColor color) {
        this.color = Objects.requireNonNull(color);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public void update() {
        position.set(positionProvider.getPosition());
        scale = session.getScale(position);
    }

    @Override
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectPoint(position, scale);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection, priority));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        particle.setPosition(position);
        particle.setSize(scale / 16);
        if (rotationProvider != null) {
            particle.setRotation(rotationProvider.getRotation());
            particle.setBillboard(false);
        } else {
            particle.setBillboard(true);
        }
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
