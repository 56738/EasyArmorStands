package me.m56738.easyarmorstands.capability.particle;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.Color;
import org.bukkit.entity.Player;

@Capability(name = "Dust particles")
public interface DustParticleCapability {
    void spawnParticle(Player player, double x, double y, double z, Color color);

    double getDensity();
}
