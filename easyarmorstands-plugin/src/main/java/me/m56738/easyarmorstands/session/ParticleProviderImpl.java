package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import org.jetbrains.annotations.NotNull;

class ParticleProviderImpl implements ParticleProvider {
    private final SessionImpl session;
    private final ParticleCapability particleCapability;

    ParticleProviderImpl(SessionImpl session) {
        this.session = session;
        this.particleCapability = EasyArmorStandsPlugin.getInstance().getCapability(ParticleCapability.class);
    }

    @Override
    public @NotNull PointParticle createPoint() {
        return particleCapability.createPoint(session.getWorld());
    }

    @Override
    public @NotNull LineParticle createLine() {
        return particleCapability.createLine(session.getWorld());
    }

    @Override
    public @NotNull CircleParticle createCircle() {
        return particleCapability.createCircle(session.getWorld());
    }

    @Override
    public @NotNull BoundingBoxParticle createAxisAlignedBox() {
        return particleCapability.createAxisAlignedBox(session.getWorld());
    }
}
