package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.lib.gizmo.api.GizmoFactory;
import org.jetbrains.annotations.NotNull;

public class GizmoParticleProvider implements ParticleProvider {
    private final GizmoFactory gizmoFactory;

    public GizmoParticleProvider(GizmoFactory gizmoFactory) {
        this.gizmoFactory = gizmoFactory;
    }

    @Override
    public @NotNull GizmoPointParticle createPoint() {
        return new GizmoPointParticle(gizmoFactory.createPoint());
    }

    @Override
    public @NotNull GizmoLineParticle createLine() {
        return new GizmoLineParticle(gizmoFactory.createLine());
    }

    @Override
    public @NotNull GizmoCircleParticle createCircle() {
        return new GizmoCircleParticle(gizmoFactory.createCircle());
    }

    @Override
    public @NotNull LineBoundingBoxParticle createAxisAlignedBox() {
        return new LineBoundingBoxParticle(this::createLine);
    }

    @Override
    public boolean isVisibleThroughWalls() {
        return gizmoFactory.isVisibleThroughWalls();
    }
}
