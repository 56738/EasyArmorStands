package me.m56738.easyarmorstands.capability.particle;

import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.particle.CircleParticle;
import me.m56738.easyarmorstands.particle.ColoredParticle;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

@Capability(name = "Particles")
public interface ParticleCapability {
    default PointParticle createPoint(World world) {
        return new SimplePointParticle(this);
    }

    default LineParticle createLine(World world) {
        return new SimpleLineParticle(this);
    }

    default CircleParticle createCircle(World world) {
        return new SimpleCircleParticle(this);
    }

    default AxisAlignedBoxParticle createAxisAlignedBox(World world) {
        return new SimpleAxisAlignedBoxParticle(world, this);
    }

    default boolean isLineVisibleThroughWall() {
        return false;
    }

    @Deprecated
    void spawnParticle(Player player, double x, double y, double z, Color color);

    @Deprecated
    double getDensity();

    abstract class SimpleColoredParticle implements ColoredParticle {
        protected final ParticleCapability capability;
        protected final List<Player> players = new ArrayList<>();
        protected ParticleColor color = ParticleColor.WHITE;

        protected SimpleColoredParticle(ParticleCapability capability) {
            this.capability = capability;
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

    class SimplePointParticle extends SimpleColoredParticle implements PointParticle {
        private final Vector3d position = new Vector3d();

        protected SimplePointParticle(ParticleCapability capability) {
            super(capability);
        }

        @Override
        public void update() {
            Color color = Color.fromRGB(this.color.red(), this.color.green(), this.color.blue());
            for (Player player : players) {
                capability.spawnParticle(player, position.x(), position.y(), position.z(), color);
            }
        }

        @Override
        public double getSize() {
            return 0;
        }

        @Override
        public void setSize(double size) {
        }

        @Override
        public Vector3dc getPosition() {
            return position;
        }

        @Override
        public void setPosition(Vector3dc position) {
            this.position.set(position);
        }

        @Override
        public Quaterniondc getRotation() {
            return Util.IDENTITY;
        }

        @Override
        public void setRotation(Quaterniondc rotation) {
        }

        @Override
        public boolean isBillboard() {
            return true;
        }

        @Override
        public void setBillboard(boolean billboard) {
        }
    }

    class SimpleLineParticle extends SimpleColoredParticle implements LineParticle {
        private final Vector3d center = new Vector3d();
        private final Quaterniond rotation = new Quaterniond();
        private Axis axis = Axis.Z;
        private double length;

        protected SimpleLineParticle(ParticleCapability capability) {
            super(capability);
        }

        @Override
        public void update() {
            Color color = Color.fromRGB(this.color.red(), this.color.green(), this.color.blue());
            Vector3d direction = axis.getDirection().rotate(rotation, new Vector3d());
            Vector3d start = center.fma(-length / 2, direction, new Vector3d());
            Vector3d end = center.fma(length / 2, direction, new Vector3d());
            double length = start.distance(end);
            double x = start.x();
            double y = start.y();
            double z = start.z();
            double dx = end.x() - start.x();
            double dy = end.y() - start.y();
            double dz = end.z() - start.z();
            int count = Math.min((int) Math.round(length * capability.getDensity()), 100);
            if (count < 1) {
                return;
            }
            for (int i = 0; i < count; i++) {
                double t = i / (double) count;
                for (Player player : players) {
                    capability.spawnParticle(player,
                            x + t * dx,
                            y + t * dy,
                            z + t * dz,
                            color);
                }
            }
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
        public Axis getAxis() {
            return axis;
        }

        @Override
        public void setAxis(Axis axis) {
            this.axis = axis;
        }

        @Override
        public double getWidth() {
            return 0;
        }

        @Override
        public void setWidth(double width) {
        }

        @Override
        public Quaterniondc getRotation() {
            return rotation;
        }

        @Override
        public void setRotation(Quaterniondc rotation) {
            this.rotation.set(rotation);
        }

        @Override
        public double getLength() {
            return length;
        }

        @Override
        public void setLength(double length) {
            this.length = length;
        }
    }

    class SimpleCircleParticle extends SimpleColoredParticle implements CircleParticle {
        private final Vector3d center = new Vector3d();
        private final Quaterniond rotation = new Quaterniond();
        private Axis axis;
        private double radius;

        protected SimpleCircleParticle(ParticleCapability capability) {
            super(capability);
        }

        @Override
        public void update() {
            Axis side = axis == Axis.Y ? Axis.X : Axis.Y;
            Color color = Color.fromRGB(this.color.red(), this.color.green(), this.color.blue());
            double circumference = 2 * Math.PI * radius;
            int count = Math.min((int) Math.round(circumference * capability.getDensity()), 100);
            Vector3d offset = side.getDirection().mul(radius, new Vector3d()).rotate(rotation);
            Vector3dc axisDirection = axis.getDirection().rotate(rotation, new Vector3d());
            double axisX = axisDirection.x();
            double axisY = axisDirection.y();
            double axisZ = axisDirection.z();
            double angle = 2 * Math.PI / count;
            for (int i = 0; i < count; i++) {
                offset.rotateAxis(angle, axisX, axisY, axisZ);
                for (Player player : players) {
                    capability.spawnParticle(player,
                            center.x() + offset.x,
                            center.y() + offset.y,
                            center.z() + offset.z,
                            color);
                }
            }
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
        public Axis getAxis() {
            return axis;
        }

        @Override
        public void setAxis(Axis axis) {
            this.axis = axis;
        }

        @Override
        public double getWidth() {
            return 0;
        }

        @Override
        public void setWidth(double width) {
        }

        @Override
        public Quaterniondc getRotation() {
            return rotation;
        }

        @Override
        public void setRotation(Quaterniondc rotation) {
            this.rotation.set(rotation);
        }

        @Override
        public double getRadius() {
            return radius;
        }

        @Override
        public void setRadius(double radius) {
            this.radius = radius;
        }
    }

    class SimpleAxisAlignedBoxParticle implements AxisAlignedBoxParticle {
        private final Line[] lines;
        private final Vector3d center = new Vector3d();
        private final Vector3d size = new Vector3d();
        private ParticleColor color = ParticleColor.WHITE;

        public SimpleAxisAlignedBoxParticle(World world, ParticleCapability capability) {
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
}
