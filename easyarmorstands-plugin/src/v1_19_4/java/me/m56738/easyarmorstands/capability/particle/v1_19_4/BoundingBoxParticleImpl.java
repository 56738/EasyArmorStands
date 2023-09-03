package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoundingBoxParticleImpl implements BoundingBoxParticle {
    private final Line[] lines;
    private BoundingBox box = BoundingBox.of(new Vector3d());
    private ParticleColor color = ParticleColor.WHITE;

    public BoundingBoxParticleImpl(World world, ParticleCapability capability) {
        this.lines = new Line[]{
                createLine(world, capability, 0.5, 0, 0, Axis.X),
                createLine(world, capability, 0.5, 0, 1, Axis.X),
                createLine(world, capability, 0.5, 1, 0, Axis.X),
                createLine(world, capability, 0.5, 1, 1, Axis.X),
                createLine(world, capability, 0, 0.5, 0, Axis.Y),
                createLine(world, capability, 0, 0.5, 1, Axis.Y),
                createLine(world, capability, 1, 0.5, 0, Axis.Y),
                createLine(world, capability, 1, 0.5, 1, Axis.Y),
                createLine(world, capability, 0, 0, 0.5, Axis.Z),
                createLine(world, capability, 1, 0, 0.5, Axis.Z),
                createLine(world, capability, 0, 1, 0.5, Axis.Z),
                createLine(world, capability, 1, 1, 0.5, Axis.Z)
        };
    }

    private Line createLine(World world, ParticleCapability capability, double x, double y, double z, Axis axis) {
        return new Line(world, axis, new Vector3d(x, y, z), capability);
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setBoundingBox(@NotNull BoundingBox box) {
        if (!this.box.equals(box)) {
            this.box = BoundingBox.of(box);
            refresh();
        }
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
    public @NotNull ParticleColor getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull ParticleColor color) {
        this.color = color;
        for (Line line : lines) {
            line.particle.setColor(color);
        }
    }

    private void refresh() {
        for (Line line : lines) {
            line.refresh(box);
        }
    }

    @Override
    public void show(@NotNull Player player) {
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
    public void hide(@NotNull Player player) {
        for (Line line : lines) {
            line.particle.hide(player);
        }
    }

    private static class Line {
        private final Axis axis;
        private final Vector3dc offset;
        private final LineParticle particle;
        private final Vector3d position = new Vector3d();
        private final Vector3d size = new Vector3d();

        private Line(World world, Axis axis, Vector3dc offset, ParticleCapability capability) {
            this.axis = axis;
            this.offset = offset;
            this.particle = capability.createLine(world);
            this.particle.setAxis(axis);
        }

        public void refresh(BoundingBox box) {
            Vector3dc min = box.getMinPosition();
            Vector3dc max = box.getMaxPosition();
            max.sub(min, size);
            min.fma(offset, size, position);
            particle.setCenter(position);
            particle.setLength(axis.getDirection().dot(size) + particle.getWidth());
        }
    }
}
