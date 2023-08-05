package me.m56738.easyarmorstands.capability.particle;

import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.particle.CircleParticle;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.PointParticle;
import org.bukkit.World;

@Capability(name = "Particles")
public interface ParticleCapability {
    PointParticle createPoint(World world);

    LineParticle createLine(World world);

    CircleParticle createCircle(World world);

    AxisAlignedBoxParticle createAxisAlignedBox(World world);

    boolean isVisibleThroughWalls();
}
