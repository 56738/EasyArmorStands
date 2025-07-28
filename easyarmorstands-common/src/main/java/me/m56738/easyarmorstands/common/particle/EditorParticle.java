package me.m56738.easyarmorstands.common.particle;

import me.m56738.easyarmorstands.api.particle.Particle;

public interface EditorParticle extends Particle {
    void showGizmo();

    void updateGizmo();

    void hideGizmo();
}
