package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.lib.gizmo.api.Gizmo;

public class GizmoParticle implements EditorParticle {
    private final Gizmo gizmo;

    public GizmoParticle(Gizmo gizmo) {
        this.gizmo = gizmo;
    }

    @Override
    public void showGizmo() {
        gizmo.show();
    }

    @Override
    public void updateGizmo() {
        gizmo.update();
    }

    @Override
    public void hideGizmo() {
        gizmo.hide();
    }
}
