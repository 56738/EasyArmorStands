package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_8;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ParticleSpawner;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ParticleSpawnerImpl implements ParticleSpawner {
    @Override
    public Object getData(Color color) {
        return color;
    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z, Object data) {
        Color color = (Color) data;
        player.spigot().playEffect(
                new Location(player.getWorld(), x, y, z),
                Effect.COLOURED_DUST,
                0,
                1,
                Math.max(color.getRed() / 255f, 0.01f),
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                1,
                0,
                64
        );
    }

    public static class Provider implements ParticleSpawner.Provider {
        @Override
        public boolean isSupported() {
            try {
                Class<?> playerSpigot = Class.forName("org.bukkit.entity.Player$Spigot");
                playerSpigot.getDeclaredMethod("playEffect", Location.class, Effect.class, int.class, int.class,
                        float.class, float.class, float.class, float.class, int.class, int.class);
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
