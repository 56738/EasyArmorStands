package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.gizmo.api.GizmoAxis;
import me.m56738.gizmo.api.GizmoColor;

public final class GizmoAdapter {
    private GizmoAdapter() {
    }

    public static GizmoAxis convert(Axis axis) {
        return GizmoAxis.valueOf(axis.name());
    }

    public static GizmoColor convert(ParticleColor color) {
        return GizmoColor.valueOf(color.name());
    }

    public static Axis convert(GizmoAxis axis) {
        return Axis.valueOf(axis.name());
    }

    public static ParticleColor convert(GizmoColor color) {
        return ParticleColor.valueOf(color.name());
    }
}
