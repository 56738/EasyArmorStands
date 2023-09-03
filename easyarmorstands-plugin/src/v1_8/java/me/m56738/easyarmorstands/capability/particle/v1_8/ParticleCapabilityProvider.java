package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class ParticleCapabilityProvider implements CapabilityProvider<ParticleCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public ParticleCapability create(Plugin plugin) {
        return new ParticleCapabilityImpl();
    }

    private static class ParticleCapabilityImpl implements ParticleCapability {
        @Override
        public PointParticle createPoint(World world) {
            DustParticleCapability capability = EasyArmorStandsPlugin.getInstance().getCapability(DustParticleCapability.class);
            return new PointDustParticle(capability);
        }

        @Override
        public LineParticle createLine(World world) {
            DustParticleCapability capability = EasyArmorStandsPlugin.getInstance().getCapability(DustParticleCapability.class);
            return new LineDustParticle(capability);
        }

        @Override
        public CircleParticle createCircle(World world) {
            DustParticleCapability capability = EasyArmorStandsPlugin.getInstance().getCapability(DustParticleCapability.class);
            return new CircleDustParticle(capability);
        }

        @Override
        public BoundingBoxParticle createAxisAlignedBox(World world) {
            DustParticleCapability capability = EasyArmorStandsPlugin.getInstance().getCapability(DustParticleCapability.class);
            return new BoundingBoxDustParticle(capability);
        }

        @Override
        public boolean isVisibleThroughWalls() {
            return false;
        }
    }
}
