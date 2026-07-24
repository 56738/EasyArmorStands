package me.m56738.easyarmorstands.modded.particle;

import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.particle.GizmoParticleProvider;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import me.m56738.gizmo.modded.api.ModdedServerGizmos;

public class ModdedParticleProviderFactory implements ParticleProviderFactory {
    private final ModdedServerGizmos gizmos;

    public ModdedParticleProviderFactory(ModdedServerGizmos gizmos) {
        this.gizmos = gizmos;
    }

    @Override
    public ParticleProvider createParticleProvider(Player player) {
        return new GizmoParticleProvider(gizmos.player(ModdedPlayer.toNative(player)));
    }
}
