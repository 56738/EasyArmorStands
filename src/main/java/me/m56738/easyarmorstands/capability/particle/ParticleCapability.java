package me.m56738.easyarmorstands.capability.particle;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.Color;
import org.bukkit.entity.Player;

@Capability(name = "Particles")
public interface ParticleCapability {
    void spawnParticle(Player player, double x, double y, double z, Color color);

    double getDensity();
}
