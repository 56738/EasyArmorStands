package me.m56738.easyarmorstands.capability.particle.v1_13;

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
            Player.class.getDeclaredMethod("spawnParticle", Particle.class, double.class, double.class, double.class, int.class, Object.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public ParticleCapability create(Plugin plugin) {
        return new ParticleCapabilityImpl();
    }

    private static class ParticleCapabilityImpl implements ParticleCapability {
        @Override
        public void spawnParticle(Player player, double x, double y, double z, Color color) {
            player.spawnParticle(Particle.REDSTONE, x, y, z, 1, new Particle.DustOptions(color, 0.5f));
        }

        @Override
        public double getDensity() {
            return 5;
        }
    }
}
