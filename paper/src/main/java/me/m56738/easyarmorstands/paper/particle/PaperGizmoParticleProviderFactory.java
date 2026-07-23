package me.m56738.easyarmorstands.paper.particle;

import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.particle.GizmoParticleProvider;
import me.m56738.easyarmorstands.particle.ParticleProviderFactory;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.gizmo.bukkit.api.BukkitGizmos;

public class PaperGizmoParticleProviderFactory implements ParticleProviderFactory {
    private final BukkitGizmos gizmos;

    public PaperGizmoParticleProviderFactory(BukkitGizmos gizmos) {
        this.gizmos = gizmos;
    }

    @Override
    public ParticleProvider createParticleProvider(Player player) {
        return new GizmoParticleProvider(gizmos.player(PaperPlayer.toNative(player)));
    }
}
