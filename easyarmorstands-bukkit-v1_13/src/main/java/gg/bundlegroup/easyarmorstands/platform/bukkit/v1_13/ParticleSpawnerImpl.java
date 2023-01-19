package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_13;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ParticleSpawner;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleSpawnerImpl implements ParticleSpawner {
    @Override
    public Object getData(Color color) {
        return new Particle.DustOptions(color, 0.5f);
    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z, Object data) {
        player.spawnParticle(Particle.REDSTONE, x, y, z, 1, data);
    }

    public static class Provider implements ParticleSpawner.Provider {
        @Override
        public boolean isSupported() {
            try {
                Class.forName("org.bukkit.Particle$DustOptions");
                return true;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        @Override
        public Priority getPriority() {
            return Priority.HIGH;
        }

        @Override
        public ParticleSpawner create() {
            return new ParticleSpawnerImpl();
        }
    }
}
