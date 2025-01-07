package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.api.particle.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface EditorParticle extends Particle {
    void show();

    void update();

    void hide();

    @Override
    default void show(@NotNull Player player) {
        show();
    }

    @Override
    default void hide(@NotNull Player player) {
        hide();
    }
}
