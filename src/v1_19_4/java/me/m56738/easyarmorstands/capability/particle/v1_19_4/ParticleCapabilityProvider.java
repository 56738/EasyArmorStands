package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.CircleParticle;
import me.m56738.easyarmorstands.particle.ColoredParticle;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.Particle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Particle.REDSTONE;

public class ParticleCapabilityProvider implements CapabilityProvider<ParticleCapability> {
    private JOMLMapper mapper;

    public ParticleCapabilityProvider() {
        try {
            mapper = new JOMLMapper();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isSupported() {
        if (mapper == null) {
            return false;
        }
        try {
            Player.class.getMethod("showEntity", Plugin.class, Entity.class);
            BlockDisplay.class.getMethod("setVisibleByDefault", boolean.class);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGHEST;
    }

    @Override
    public ParticleCapability create(Plugin plugin) {
        return new ParticleCapabilityImpl(mapper);
    }

    private static class ParticleCapabilityImpl implements ParticleCapability {
        private final JOMLMapper mapper;

        private ParticleCapabilityImpl(JOMLMapper mapper) {
            this.mapper = mapper;
        }

        @Override
        public PointParticle createPoint(World world) {
            return new PointParticleImpl(world, mapper);
        }

        @Override
        public LineParticle createLine(World world) {
            return new LineParticleImpl(world, mapper);
        }

        @Override
        public CircleParticle createCircle(World world) {
            return new CircleParticleImpl(world, mapper);
        }

        @Override
        public boolean isLineVisibleThroughWall() {
            return true;
        }

        @Override
        public void spawnParticle(Player player, double x, double y, double z, Color color) {
            player.spawnParticle(REDSTONE, x, y, z, 1, new DustOptions(color, 0.5f));
        }

        @Override
        public double getDensity() {
            return 5;
        }
    }

    private static abstract class ParticleImpl<T extends Entity> implements Particle {

        protected final Set<Player> players = new HashSet<>();
        private final Class<T> type;
        private final World world;
        protected T entity;
        private boolean dirty;

        private ParticleImpl(Class<T> type, World world) {
            this.type = type;
            this.world = world;
        }

        @SuppressWarnings("UnstableApiUsage")
        protected void configure(T entity) {
            entity.setPersistent(false);
            entity.setVisibleByDefault(false);
            entity.setMetadata("easyarmorstands_ignore", new FixedMetadataValue(EasyArmorStands.getInstance(), true));
            entity.setGlowing(true);
        }

        protected void update(T entity) {
        }

        protected abstract Location getLocation();

        protected void markDirty() {
            dirty = true;
        }

        private void create() {
            entity = world.spawn(getLocation(), type, e -> {
                configure(e);
                update(e);
            });
            dirty = false;
        }

        @Override
        public void update() {
            if (dirty && entity != null) {
                dirty = false;
                update(entity);
                entity.teleport(getLocation());
            }
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public void show(Player player) {
            boolean added = players.add(player);
            if (added) {
                if (players.size() == 1) {
                    create();
                }
                player.showEntity(EasyArmorStands.getInstance(), entity);
            }
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public void hide(Player player) {
            boolean removed = players.remove(player);
            if (removed) {
                if (EasyArmorStands.getInstance().isEnabled()) {
                    player.hideEntity(EasyArmorStands.getInstance(), entity);
                }
                if (players.isEmpty()) {
                    entity.remove();
                    entity = null;
                }
            }
        }
    }

    private static abstract class DisplayParticleImpl<T extends Display> extends ParticleImpl<T> {
        private DisplayParticleImpl(Class<T> type, World world) {
            super(type, world);
        }

        @Override
        protected void configure(T entity) {
            super.configure(entity);
            entity.setBrightness(new Display.Brightness(15, 0));
            entity.setInterpolationDuration(1);
        }

        @Override
        protected void update(T entity) {
            super.update(entity);
            entity.setInterpolationDelay(0);
        }
    }

    private static abstract class BlockDisplayParticleImpl extends DisplayParticleImpl<BlockDisplay> implements ColoredParticle {
        private ParticleColor color = ParticleColor.WHITE;
        private Material material = Material.WHITE_CONCRETE;

        private BlockDisplayParticleImpl(World world) {
            super(BlockDisplay.class, world);
        }

        @Override
        public ParticleColor getColor() {
            return color;
        }

        @Override
        public void setColor(ParticleColor color) {
            if (this.color == color) {
                return;
            }
            this.color = color;
            switch (color) {
                case WHITE:
                    this.material = Material.WHITE_CONCRETE;
                    break;
                case RED:
                    this.material = Material.RED_CONCRETE;
                    break;
                case GREEN:
                    this.material = Material.LIME_CONCRETE;
                    break;
                case BLUE:
                    this.material = Material.BLUE_CONCRETE;
                    break;
                case YELLOW:
                    this.material = Material.YELLOW_CONCRETE;
                    break;
                case GRAY:
                    this.material = Material.LIGHT_GRAY_CONCRETE;
                    break;
                case AQUA:
                    this.material = Material.LIGHT_BLUE_CONCRETE;
                    break;
            }
            markDirty();
        }

        @Override
        protected void update(BlockDisplay entity) {
            super.update(entity);
            entity.setBlock(material.createBlockData());
            entity.setGlowColorOverride(Util.toColor(color));
        }
    }

    private static class PointParticleImpl extends BlockDisplayParticleImpl implements PointParticle {
        private final World world;
        private final JOMLMapper mapper;
        private final Vector3d position = new Vector3d();
        private final Quaternionf rotation = new Quaternionf();
        private double size = 0.0625;
        private boolean billboard = true;

        private PointParticleImpl(World world, JOMLMapper mapper) {
            super(world);
            this.world = world;
            this.mapper = mapper;
        }

        @Override
        public double getSize() {
            return size;
        }

        @Override
        public void setSize(double size) {
            if (this.size != size) {
                this.size = size;
                markDirty();
            }
        }

        @Override
        public Vector3dc getPosition() {
            return position;
        }

        @Override
        public void setPosition(Vector3dc position) {
            if (!this.position.equals(position, 1e-6)) {
                this.position.set(position);
                markDirty();
            }
        }

        @Override
        public Quaterniondc getRotation() {
            return new Quaterniond(rotation);
        }

        @Override
        public void setRotation(Quaterniondc rotation) {
            Quaternionf rot = new Quaternionf(rotation);
            if (!this.rotation.equals(rot, 1e-6f)) {
                this.rotation.set(rot);
                markDirty();
            }
        }

        @Override
        public boolean isBillboard() {
            return billboard;
        }

        @Override
        public void setBillboard(boolean billboard) {
            if (this.billboard != billboard) {
                this.billboard = billboard;
                markDirty();
            }
        }

        @Override
        protected void update(BlockDisplay entity) {
            super.update(entity);
            Vector3f scale = new Vector3f((float) size);
            if (billboard) {
                scale.z = 0.001f;
            }
            entity.setTransformation(mapper.getTransformation(
                    scale.mul(-0.5f, new Vector3f()).rotate(rotation),
                    rotation,
                    scale,
                    new Quaternionf()));
            entity.setBillboard(billboard ? Billboard.CENTER : Billboard.FIXED);
        }

        @Override
        protected Location getLocation() {
            return new Location(world, position.x(), position.y(), position.z());
        }
    }

    private static class LineParticleImpl extends BlockDisplayParticleImpl implements LineParticle {
        private final World world;
        private final JOMLMapper mapper;
        private final Vector3d center = new Vector3d();
        private final Quaternionf rotation = new Quaternionf();
        private Axis axis = Axis.Z;
        private double width = 0.0625;
        private double length;

        private LineParticleImpl(World world, JOMLMapper mapper) {
            super(world);
            this.world = world;
            this.mapper = mapper;
        }

        @Override
        public Vector3dc getCenter() {
            return center;
        }

        @Override
        public void setCenter(Vector3dc center) {
            if (!this.center.equals(center, 1e-6)) {
                this.center.set(center);
                markDirty();
            }
        }

        @Override
        public Axis getAxis() {
            return axis;
        }

        @Override
        public void setAxis(Axis axis) {
            if (this.axis != axis) {
                this.axis = axis;
                markDirty();
            }
        }

        @Override
        public double getWidth() {
            return width;
        }

        @Override
        public void setWidth(double width) {
            if (this.width != width) {
                this.width = width;
                markDirty();
            }
        }

        @Override
        public Quaterniondc getRotation() {
            return new Quaterniond(rotation);
        }

        @Override
        public void setRotation(Quaterniondc rotation) {
            Quaternionf rot = new Quaternionf(rotation);
            if (!this.rotation.equals(rot, 1e-6f)) {
                this.rotation.set(rot);
                markDirty();
            }
        }

        @Override
        public double getLength() {
            return length;
        }

        @Override
        public void setLength(double length) {
            if (this.length != length) {
                this.length = length;
                markDirty();
            }
        }

        @Override
        protected void update(BlockDisplay entity) {
            super.update(entity);
            float mainScale = (float) length;
            float offScale = (float) width;
            Vector3fc scale;
            switch (axis) {
                case X:
                    scale = new Vector3f(mainScale, offScale, offScale);
                    break;
                case Y:
                    scale = new Vector3f(offScale, mainScale, offScale);
                    break;
                case Z:
                    scale = new Vector3f(offScale, offScale, mainScale);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            entity.setTransformation(mapper.getTransformation(
                    scale.mul(-0.5f, new Vector3f()).rotate(rotation),
                    rotation,
                    scale,
                    new Quaternionf()));
        }

        @Override
        protected Location getLocation() {
            return new Location(world, center.x(), center.y(), center.z());
        }
    }

    private static class CircleParticleImpl implements CircleParticle {
        private final int count = 20;
        private final Vector3d center = new Vector3d();
        private final Quaterniond rotation = new Quaterniond();
        private final LineParticle[] lines = new LineParticle[count];
        private Axis axis = Axis.Y;
        private double radius;
        private boolean dirty;

        public CircleParticleImpl(World world, JOMLMapper mapper) {
            for (int i = 0; i < count; i++) {
                lines[i] = new LineParticleImpl(world, mapper);
            }
        }

        @Override
        public ParticleColor getColor() {
            return lines[0].getColor();
        }

        @Override
        public void setColor(ParticleColor color) {
            for (LineParticle line : lines) {
                line.setColor(color);
            }
        }

        @Override
        public Vector3dc getCenter() {
            return center;
        }

        @Override
        public void setCenter(Vector3dc center) {
            if (!this.center.equals(center, 1e-6)) {
                this.center.set(center);
                dirty = true;
            }
        }

        @Override
        public Axis getAxis() {
            return axis;
        }

        @Override
        public void setAxis(Axis axis) {
            if (this.axis != axis) {
                this.axis = axis;
                dirty = true;
            }
        }

        @Override
        public double getWidth() {
            return lines[0].getWidth();
        }

        @Override
        public void setWidth(double width) {
            if (lines[0].getWidth() != width) {
                for (LineParticle line : lines) {
                    line.setWidth(width);
                }
                dirty = true;
            }
        }

        @Override
        public Quaterniondc getRotation() {
            return rotation;
        }

        @Override
        public void setRotation(Quaterniondc rotation) {
            if (!this.rotation.equals(rotation, 1e-6)) {
                this.rotation.set(rotation);
                dirty = true;
            }
        }

        @Override
        public double getRadius() {
            return radius;
        }

        @Override
        public void setRadius(double radius) {
            if (this.radius != radius) {
                this.radius = radius;
                dirty = true;
            }
        }

        private void updateCircle() {
            Axis forward = axis == Axis.Z ? Axis.X : Axis.Z;
            Axis side = axis == Axis.Y ? Axis.X : Axis.Y;

            Quaterniond currentRotation = new Quaterniond(rotation);
            Vector3d offset = side.getDirection().mul(radius, new Vector3d());
            double angle = 2 * Math.PI / count;
            Vector3dc axisDirection = axis.getDirection();
            double axisX = axisDirection.x();
            double axisY = axisDirection.y();
            double axisZ = axisDirection.z();
            double length = 2 * Math.tan(angle / 2) * (radius + getWidth() / 2);
            for (int i = 0; i < count; i++) {
                lines[i].setCenter(offset.rotate(currentRotation, new Vector3d()).add(center));
                lines[i].setRotation(currentRotation);
                lines[i].setLength(length);
                lines[i].setAxis(forward);
                currentRotation.rotateAxis(angle, axisX, axisY, axisZ);
            }
            dirty = false;
        }

        @Override
        public void show(Player player) {
            updateCircle();
            for (LineParticle line : lines) {
                line.show(player);
            }
        }

        @Override
        public void update() {
            if (dirty) {
                updateCircle();
            }
            for (LineParticle line : lines) {
                line.update();
            }
        }

        @Override
        public void hide(Player player) {
            for (LineParticle line : lines) {
                line.hide(player);
            }
        }
    }
}
