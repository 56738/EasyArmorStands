package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashSet;
import java.util.Set;

public class AxisAlignedBoxDustParticle implements AxisAlignedBoxParticle {
    private final DustParticleCapability capability;
    private final Set<Player> players = new HashSet<>();
    private final Vector3d center = new Vector3d();
    private final Vector3d size = new Vector3d();
    private ParticleColor color = ParticleColor.WHITE;

    public AxisAlignedBoxDustParticle(DustParticleCapability capability) {
        this.capability = capability;
    }

    @Override
    public Vector3dc getCenter() {
        return center;
    }

    @Override
    public void setCenter(Vector3dc center) {
        this.center.set(center);
    }

    @Override
    public Vector3dc getSize() {
        return size;
    }

    @Override
    public void setSize(Vector3dc size) {
        this.size.set(size);
    }

    @Override
    public double getLineWidth() {
        return 0;
    }

    @Override
    public void setLineWidth(double lineWidth) {
    }

    @Override
    public ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(ParticleColor color) {
        this.color = color;
    }

    @Override
    public void show(Player player) {
        players.add(player);
    }

    @Override
    public void update() {
        double x = center.x();
        double y = center.y();
        double z = center.z();
        double sx = size.x();
        double sy = size.y();
        double sz = size.z();
        double dx = sx / 2;
        double dy = sy / 2;
        double dz = sz / 2;
        Color color = Util.toColor(this.color);
        int cx = getParticleCount(sx);
        int cy = getParticleCount(sy);
        int cz = getParticleCount(sz);
        showLine(x - dx, y - dy, z - dz, sx, 0, 0, color, false, cx);
        showLine(x - dx, y - dy, z + dz, sx, 0, 0, color, false, cx);
        showLine(x - dx, y + dy, z - dz, sx, 0, 0, color, false, cx);
        showLine(x - dx, y + dy, z + dz, sx, 0, 0, color, false, cx);
        showLine(x - dx, y - dy, z - dz, 0, sy, 0, color, true, cy);
        showLine(x - dx, y - dy, z + dz, 0, sy, 0, color, true, cy);
        showLine(x + dx, y - dy, z - dz, 0, sy, 0, color, true, cy);
        showLine(x + dx, y - dy, z + dz, 0, sy, 0, color, true, cy);
        showLine(x - dx, y - dy, z - dz, 0, 0, sz, color, false, cz);
        showLine(x + dx, y - dy, z - dz, 0, 0, sz, color, false, cz);
        showLine(x - dx, y + dy, z - dz, 0, 0, sz, color, false, cz);
        showLine(x + dx, y + dy, z - dz, 0, 0, sz, color, false, cz);
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
    public void hide(Player player) {
        players.remove(player);
    }
}
