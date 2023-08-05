package me.m56738.easyarmorstands.particle;

import org.bukkit.entity.Player;

public interface Particle {
    void show(Player player);

    void update();

    void hide(Player player);
}
