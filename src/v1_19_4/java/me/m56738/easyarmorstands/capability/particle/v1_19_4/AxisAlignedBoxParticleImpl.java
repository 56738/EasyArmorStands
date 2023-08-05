package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Axis;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class AxisAlignedBoxParticleImpl implements AxisAlignedBoxParticle {
    private final Line[] lines;
    private final Vector3d center = new Vector3d();
    private final Vector3d size = new Vector3d();
    private ParticleColor color = ParticleColor.WHITE;

    public AxisAlignedBoxParticleImpl(World world, ParticleCapability capability) {
        this.lines = new Line[]{
                createLine(world, capability, 0, -1, -1, Axis.X),
                createLine(world, capability, 0, -1, +1, Axis.X),
                createLine(world, capability, 0, +1, -1, Axis.X),
                createLine(world, capability, 0, +1, +1, Axis.X),
                createLine(world, capability, -1, 0, -1, Axis.Y),
                createLine(world, capability, -1, 0, +1, Axis.Y),
                createLine(world, capability, +1, 0, -1, Axis.Y),
                createLine(world, capability, +1, 0, +1, Axis.Y),
                createLine(world, capability, -1, -1, 0, Axis.Z),
                createLine(world, capability, +1, -1, 0, Axis.Z),
                createLine(world, capability, -1, +1, 0, Axis.Z),
                createLine(world, capability, +1, +1, 0, Axis.Z)
        };
    }

    private Line createLine(World world, ParticleCapability capability, int x, int y, int z, Axis axis) {
        return new Line(world, axis, new Vector3d(x, y, z), capability);
    }

    @Override
    public Vector3dc getCenter() {
        return center;
    }

    @Override
    public void setCenter(Vector3dc center) {
        this.center.set(center);
        refresh();
    }

    @Override
    public Vector3dc getSize() {
        return size;
    }

    @Override
    public void setSize(Vector3dc size) {
        this.size.set(size);
        refresh();
    }

    @Override
    public double getLineWidth() {
        return lines[0].particle.getWidth();
    }

    @Override
    public void setLineWidth(double lineWidth) {
        for (Line line : lines) {
            line.particle.setWidth(lineWidth);
        }
    }

    @Override
    public ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(ParticleColor color) {
        this.color = color;
        for (Line line : lines) {
            line.particle.setColor(color);
        }
    }

    private void refresh() {
        for (Line line : lines) {
            line.refresh(center, size);
        }
    }

    @Override
    public void show(Player player) {
        for (Line line : lines) {
            line.particle.show(player);
        }
    }

    @Override
    public void update() {
        for (Line line : lines) {
            line.particle.update();
        }
    }

    @Override
    public void hide(Player player) {
        for (Line line : lines) {
            line.particle.hide(player);
        }
    }

    private static class Line {
        private final Axis axis;
        private final Vector3dc offset;
        private final LineParticle particle;
        private final Vector3d position = new Vector3d();

        private Line(World world, Axis axis, Vector3dc offset, ParticleCapability capability) {
            this.axis = axis;
            this.offset = offset.div(2, new Vector3d());
            this.particle = capability.createLine(world);
            this.particle.setAxis(axis);
        }

        public void refresh(Vector3dc center, Vector3dc size) {
            particle.setCenter(center.fma(offset, size, position));
            particle.setLength(Math.abs(axis.getDirection().dot(size)) + particle.getWidth());
        }
    }
}
