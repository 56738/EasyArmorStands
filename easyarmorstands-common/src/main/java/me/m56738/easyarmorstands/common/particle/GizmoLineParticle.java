package me.m56738.easyarmorstands.common.particle;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.gizmo.api.LineGizmo;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class GizmoLineParticle extends GizmoParticle implements LineParticle {
    private final LineGizmo gizmo;
    private double offset = 0;
    private Axis axis = Axis.Y;
    private ParticleColor color = ParticleColor.WHITE;

    public GizmoLineParticle(LineGizmo gizmo) {
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
    public double getLength() {
        return gizmo.getLength();
    }

    @Override
    public void setLength(double length) {
        gizmo.setLength(length);
    }

    @Override
    public double getOffset() {
        return offset;
    }

    @Override
    public void setOffset(double offset) {
        this.offset = offset;
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

    @Override
    public void showGizmo() {
        updateOffset();
        super.showGizmo();
    }

    @Override
    public void updateGizmo() {
        updateOffset();
        super.updateGizmo();
    }

    private void updateOffset() {
        gizmo.setOffset(getAxis().getDirection().mul(offset - getLength() / 2, new Vector3d()).rotate(getRotation()));
    }
}
