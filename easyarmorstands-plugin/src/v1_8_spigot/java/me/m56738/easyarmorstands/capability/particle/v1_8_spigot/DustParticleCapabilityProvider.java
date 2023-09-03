package me.m56738.easyarmorstands.capability.particle.v1_8_spigot;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DustParticleCapabilityProvider implements CapabilityProvider<DustParticleCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class<?> playerSpigot = Class.forName("org.bukkit.entity.Player$Spigot");
            playerSpigot.getDeclaredMethod("playEffect", Location.class, Effect.class, int.class, int.class,
                    float.class, float.class, float.class, float.class, int.class, int.class);
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
    public DustParticleCapability create(Plugin plugin) {
        return new DustParticleCapabilityImpl();
    }

    private static class DustParticleCapabilityImpl implements DustParticleCapability {
        @Override
        public void spawnParticle(Player player, double x, double y, double z, Color color) {
            player.spigot().playEffect(
                    new Location(player.getWorld(), x, y, z),
                    Effect.COLOURED_DUST,
                    0,
                    1,
                    Math.max(color.getRed() / 255f, Float.MIN_VALUE),
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    1,
                    0,
                    64
            );
        }

        @Override
        public double getDensity() {
            return 3;
        }
    }
}
