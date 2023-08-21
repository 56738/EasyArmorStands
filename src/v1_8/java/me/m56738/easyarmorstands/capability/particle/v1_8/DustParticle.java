package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import me.m56738.easyarmorstands.api.particle.ColoredParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.joml.Math;

import java.util.HashSet;
import java.util.Set;

public abstract class DustParticle implements ColoredParticle {
    protected final DustParticleCapability capability;
    protected final Set<Player> players = new HashSet<>();
    protected ParticleColor color = ParticleColor.WHITE;

    protected DustParticle(DustParticleCapability capability) {
        this.capability = capability;
    }

    public static void showLine(Set<Player> players, DustParticleCapability capability,
                                double x, double y, double z,
                                double dx, double dy, double dz,
                                Color color, boolean includeEnds, int count) {
        if (count < 1) {
            return;
        }
        int min = includeEnds ? 0 : 1;
        int max = includeEnds ? count : count - 1;
        for (int i = min; i <= max; i++) {
            double t = i / (double) count;
            double px = x + t * dx;
            double py = y + t * dy;
            double pz = z + t * dz;
            for (Player player : players) {
                capability.spawnParticle(player, px, py, pz, color);
            }
        }
    }

    public static int getCount(DustParticleCapability capability, double length) {
        return Math.min((int) Math.round(length * capability.getDensity()), 100);
    }

    @Override
    public void show(Player player) {
        players.add(player);
    }

    @Override
    public void hide(Player player) {
        players.remove(player);
    }

    @Override
    public ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(ParticleColor color) {
        this.color = color;
    }
}
