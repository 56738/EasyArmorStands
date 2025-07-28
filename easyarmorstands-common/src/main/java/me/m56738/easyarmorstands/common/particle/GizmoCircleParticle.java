package me.m56738.easyarmorstands.common.particle;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.gizmo.api.CircleGizmo;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class GizmoCircleParticle extends GizmoParticle implements CircleParticle, EditorParticle {
    private final CircleGizmo gizmo;
    private Axis axis = Axis.Y;
    private ParticleColor color = ParticleColor.WHITE;

    public GizmoCircleParticle(CircleGizmo gizmo) {
        super(gizmo);
        this.gizmo = gizmo;
    }

    @Override
    public @NotNull Vector3dc getCenter() {
        return gizmo.getPosition();
    }

    @Override
    public void setCenter(@NotNull Vector3dc center) {
        gizmo.setPosition(center);
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public void setAxis(@NotNull Axis axis) {
        this.axis = axis;
        gizmo.setAxis(GizmoAdapter.convert(axis));
    }

    @Override
    public double getWidth() {
        return gizmo.getWidth();
    }

    @Override
    public void setWidth(double width) {
        gizmo.setWidth(width);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return gizmo.getRotation();
    }

    @Override
    public void setRotation(@NotNull Quaterniondc rotation) {
        gizmo.setRotation(rotation);
    }

    @Override
    public double getRadius() {
        return gizmo.getRadius();
    }

    @Override
    public void setRadius(double radius) {
        gizmo.setRadius(radius);
    }

    @Override
    public @NotNull ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull ParticleColor color) {
        this.color = color;
        gizmo.setColor(GizmoAdapter.convert(color));
    }
}
