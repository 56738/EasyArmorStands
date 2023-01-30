package me.m56738.easyarmorstands.bukkit.feature.v1_9;

import me.m56738.easyarmorstands.bukkit.feature.ParticleSpawner;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleSpawnerImpl implements ParticleSpawner {
    @Override
    public Object getData(RGBLike color) {
        return color;
    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z, Object data) {
        RGBLike color = (RGBLike) data;
        player.spawnParticle(Particle.REDSTONE, x, y, z, 0,
                color.red() / 255.0,
                color.green() / 255.0,
                color.blue() / 255.0,
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
            } catch (Throwable e) {
                return false;
            }
        }

        @Override
        public ParticleSpawner create() {
            return new ParticleSpawnerImpl();
        }
    }
}
