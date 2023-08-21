package me.m56738.easyarmorstands.api.particle;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface Particle {
    void show(Player player);

    void update();

    void hide(Player player);
}
