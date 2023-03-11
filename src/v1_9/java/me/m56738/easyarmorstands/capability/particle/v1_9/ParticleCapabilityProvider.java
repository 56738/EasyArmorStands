package me.m56738.easyarmorstands.capability.particle.v1_9;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ParticleCapabilityProvider implements CapabilityProvider<ParticleCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class<?> particle = Class.forName("org.bukkit.Particle");
            Player.class.getDeclaredMethod("spawnParticle", particle,
                    double.class, double.class, double.class, int.class,
                    double.class, double.class, double.class, double.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public ParticleCapability create(Plugin plugin) {
        return new ParticleCapabilityImpl();
    }

    private static class ParticleCapabilityImpl implements ParticleCapability {
        @Override
        public void spawnParticle(Player player, double x, double y, double z, Color color) {
            player.spawnParticle(Particle.REDSTONE, x, y, z, 0,
                    color.getRed() / 255.0,
                    color.getGreen() / 255.0,
                    color.getBlue() / 255.0,
                    1);
        }

        @Override
        public double getDensity() {
            return 3;
        }
    }
}
