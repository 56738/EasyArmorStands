package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_9;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ParticleSpawner;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleSpawnerImpl implements ParticleSpawner {
    @Override
    public Object getData(Color color) {
        return color;
    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z, Object data) {
        Color color = (Color) data;
        player.spawnParticle(Particle.REDSTONE, x, y, z, 0,
                color.getRed() / 255.0,
                color.getGreen() / 255.0,
                color.getBlue() / 255.0,
                1);
    }

    public static class Provider implements ParticleSpawner.Provider {
        @Override
        public boolean isSupported() {
            try {
                Class<?> particle = Class.forName("org.bukkit.Particle");
                Player.class.getDeclaredMethod("spawnParticle", particle,
                        double.class, double.class, double.class, int.class,
                        double.class, double.class, double.class, double.class);
                return true;
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                return false;
            }
        }

        @Override
        public ParticleSpawner create() {
            return new ParticleSpawnerImpl();
        }
    }
}
