package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.lib.gizmo.api.PointGizmo;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class GizmoPointParticle extends GizmoParticle implements PointParticle {
    private final PointGizmo gizmo;
    private ParticleColor color = ParticleColor.WHITE;

    public GizmoPointParticle(PointGizmo gizmo) {
        super(gizmo);
        this.gizmo = gizmo;
        this.gizmo.setBillboard(true);
    }

    @Override
    public double getSize() {
        return gizmo.getSize();
    }

    @Override
    public void setSize(double size) {
        gizmo.setSize(size);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return gizmo.getPosition();
    }

    @Override
    public void setPosition(@NotNull Vector3dc position) {
        gizmo.setPosition(position);
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
    public boolean isBillboard() {
        return gizmo.isBillboard();
    }

    @Override
    public void setBillboard(boolean billboard) {
        gizmo.setBillboard(billboard);
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
