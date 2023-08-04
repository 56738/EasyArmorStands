package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.ColoredParticle;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.Particle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.*;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.joml.*;

import java.lang.Math;
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
        public LineParticle createLine() {
            return new LineParticleImpl(mapper);
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

    private static abstract class ParticleImpl implements Particle {
        protected final Set<Player> players = new HashSet<>();

        protected abstract void create();

        protected abstract void remove();

        @Override
        public void show(Player player) {
            boolean added = players.add(player);
            if (added && players.size() == 1) {
                create();
            }
        }

        @Override
        public void hide(Player player) {
            boolean removed = players.remove(player);
            if (removed && players.isEmpty()) {
                remove();
            }
        }
    }

    private static abstract class ColoredParticleImpl extends ParticleImpl implements ColoredParticle {
        protected Material material = Material.WHITE_CONCRETE;

        @Override
        public ParticleColor getColor() {
            switch (material) {
                case RED_CONCRETE:
                    return ParticleColor.RED;
                case LIME_CONCRETE:
                    return ParticleColor.GREEN;
                case BLUE_CONCRETE:
                    return ParticleColor.BLUE;
                case YELLOW_CONCRETE:
                    return ParticleColor.YELLOW;
                case LIGHT_GRAY_CONCRETE:
                    return ParticleColor.GRAY;
                case LIGHT_BLUE_CONCRETE:
                    return ParticleColor.AQUA;
                default:
                    return ParticleColor.WHITE;
            }
        }

        @Override
        public void setColor(ParticleColor color) {
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
        }
    }

    private static class LineParticleImpl extends ColoredParticleImpl implements LineParticle {
        private final JOMLMapper mapper;
        private final Vector3d center = new Vector3d();
        private final Quaternionf rotation = new Quaternionf();
        private Axis axis = Axis.Z;
        private double width = 0.0625;
        private double length;
        private boolean dirty;
        private BlockDisplay entity;
        private Color color = Util.toColor(ParticleColor.WHITE);
        private Material currentMaterial = Material.WHITE_CONCRETE;

        private LineParticleImpl(JOMLMapper mapper) {
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
            return width;
        }

        @Override
        public void setWidth(double width) {
            if (this.width != width) {
                this.width = width;
                dirty = true;
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
                dirty = true;
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
                dirty = true;
            }
        }

        @Override
        public void setColor(ParticleColor color) {
            super.setColor(color);
            this.color = Util.toColor(color);
            if (entity != null) {
                entity.setGlowColorOverride(this.color);
            }
            if (currentMaterial != material) {
                currentMaterial = material;
                if (entity != null) {
                    entity.setBlock(material.createBlockData());
                }
            }
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public void show(Player player) {
            super.show(player);
            player.showEntity(EasyArmorStands.getInstance(), entity);
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public void hide(Player player) {
            player.hideEntity(EasyArmorStands.getInstance(), entity);
            super.hide(player);
        }

        @Override
        public void update() {
            if (entity == null || !dirty) {
                return;
            }
            dirty = false;
            World world = Bukkit.getWorlds().get(0);
            Location location = new Location(world, center.x, center.y, center.z);
            entity.teleport(location);
            configure(entity);
            entity.setInterpolationDelay(0);
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        protected void create() {
            World world = Bukkit.getWorlds().get(0);
            Location location = new Location(world, center.x, center.y, center.z);
            dirty = false;
            entity = world.spawn(location, BlockDisplay.class, e -> {
                e.setPersistent(false);
                e.setVisibleByDefault(false);
                e.setBrightness(new Display.Brightness(15, 0));
                e.setInterpolationDuration(1);
                e.setMetadata("easyarmorstands_ignore", new FixedMetadataValue(EasyArmorStands.getInstance(), true));
                e.setBlock(currentMaterial.createBlockData());
                e.setGlowColorOverride(color);
                e.setGlowing(true);
                configure(e);
            });
        }

        private void configure(BlockDisplay entity) {
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
            Vector3fc offset = scale.mul(-0.5f, new Vector3f()).rotate(rotation);
            entity.setDisplayWidth(Math.max(Math.abs(offset.x()), Math.abs(offset.z())));
            entity.setDisplayHeight(Math.abs(offset.y()));
            entity.setTransformation(mapper.getTransformation(
                    offset,
                    rotation,
                    scale,
                    new Quaternionf()));
        }

        @Override
        protected void remove() {
            entity.remove();
            entity = null;
        }
    }
}
