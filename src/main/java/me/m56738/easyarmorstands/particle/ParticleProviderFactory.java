package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface ParticleProviderFactory {
    ParticleProvider createParticleProvider(Player player);

    boolean isParticle(Entity entity);
}
