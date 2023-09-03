package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashSet;
import java.util.Set;

public class BoundingBoxDustParticle implements BoundingBoxParticle {
    private final DustParticleCapability capability;
    private final Set<Player> players = new HashSet<>();
    private BoundingBox box = BoundingBox.of(new Vector3d());
    private ParticleColor color = ParticleColor.WHITE;

    public BoundingBoxDustParticle(DustParticleCapability capability) {
        this.capability = capability;
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setBoundingBox(@NotNull BoundingBox box) {
        this.box = BoundingBox.of(box);
    }

    @Override
    public double getLineWidth() {
        return 0;
    }

    @Override
    public void setLineWidth(double lineWidth) {
    }

    @Override
    public @NotNull ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull ParticleColor color) {
        this.color = color;
    }

    @Override
    public void show(@NotNull Player player) {
        players.add(player);
    }

    @Override
    public void update() {
        Vector3dc min = box.getMinPosition();
        Vector3dc max = box.getMaxPosition();
        double x1 = min.x();
        double y1 = min.y();
        double z1 = min.z();
        double x2 = max.x();
        double y2 = max.y();
        double z2 = max.z();
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        Color color = Util.toColor(this.color);
        int cx = getParticleCount(dx);
        int cy = getParticleCount(dy);
        int cz = getParticleCount(dz);
        showLine(x1, y1, z1, dx, 0, 0, color, false, cx);
        showLine(x1, y1, z2, dx, 0, 0, color, false, cx);
        showLine(x1, y2, z1, dx, 0, 0, color, false, cx);
        showLine(x1, y2, z2, dx, 0, 0, color, false, cx);
        showLine(x1, y1, z1, 0, dy, 0, color, true, cy);
        showLine(x1, y1, z2, 0, dy, 0, color, true, cy);
        showLine(x2, y1, z1, 0, dy, 0, color, true, cy);
        showLine(x2, y1, z2, 0, dy, 0, color, true, cy);
        showLine(x1, y1, z1, 0, 0, dz, color, false, cz);
        showLine(x2, y1, z1, 0, 0, dz, color, false, cz);
        showLine(x1, y2, z1, 0, 0, dz, color, false, cz);
        showLine(x2, y2, z1, 0, 0, dz, color, false, cz);
    }

    private void showLine(double x, double y, double z,
                          double dx, double dy, double dz,
                          Color color, boolean includeEnds, int count) {
        DustParticle.showLine(players, capability, x, y, z, dx, dy, dz, color, includeEnds, count);
    }

    private int getParticleCount(double length) {
        return DustParticle.getCount(capability, length);
    }

    @Override
    public void hide(@NotNull Player player) {
        players.remove(player);
    }
}
