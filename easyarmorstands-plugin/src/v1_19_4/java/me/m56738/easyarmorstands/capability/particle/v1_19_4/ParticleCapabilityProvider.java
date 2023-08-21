package me.m56738.easyarmorstands.capability.particle.v1_19_4;

import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
        public AxisAlignedBoxParticle createAxisAlignedBox(World world) {
            return new AxisAlignedBoxParticleImpl(world, this);
        }

        @Override
        public boolean isVisibleThroughWalls() {
            return true;
        }
    }
}
