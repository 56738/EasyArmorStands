package me.m56738.easyarmorstands.api.particle;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface Particle {
    @Deprecated
    void show(@NotNull Player player);

    @Deprecated
    void update();

    @Deprecated
    void hide(@NotNull Player player);
}
