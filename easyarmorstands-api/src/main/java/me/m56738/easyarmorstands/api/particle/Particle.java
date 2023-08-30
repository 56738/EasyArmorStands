package me.m56738.easyarmorstands.api.particle;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface Particle {
    void show(@NotNull Player player);

    void update();

    void hide(@NotNull Player player);
}
