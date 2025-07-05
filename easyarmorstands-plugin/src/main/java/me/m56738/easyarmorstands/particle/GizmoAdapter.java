package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.lib.gizmo.api.GizmoAxis;
import me.m56738.easyarmorstands.lib.gizmo.api.color.GizmoColor;

import java.util.EnumMap;
import java.util.Map;

public final class GizmoAdapter {
    private static final Map<Axis, GizmoAxis> AXIS_MAP = new EnumMap<>(Axis.class);
    private static final Map<ParticleColor, GizmoColor> COLOR_MAP = new EnumMap<>(ParticleColor.class);

    static {
        AXIS_MAP.put(Axis.X, GizmoAxis.X);
        AXIS_MAP.put(Axis.Y, GizmoAxis.Y);
        AXIS_MAP.put(Axis.Z, GizmoAxis.Z);
        COLOR_MAP.put(ParticleColor.WHITE, GizmoColor.WHITE);
        COLOR_MAP.put(ParticleColor.RED, GizmoColor.RED);
        COLOR_MAP.put(ParticleColor.GREEN, GizmoColor.GREEN);
        COLOR_MAP.put(ParticleColor.BLUE, GizmoColor.BLUE);
        COLOR_MAP.put(ParticleColor.YELLOW, GizmoColor.YELLOW);
        COLOR_MAP.put(ParticleColor.GRAY, GizmoColor.GRAY);
        COLOR_MAP.put(ParticleColor.AQUA, GizmoColor.AQUA);
    }

    private GizmoAdapter() {
    }

    public static GizmoAxis convert(Axis axis) {
        return AXIS_MAP.get(axis);
    }

    public static GizmoColor convert(ParticleColor color) {
        return COLOR_MAP.get(color);
    }
}
