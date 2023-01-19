package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import org.bukkit.Color;
import org.bukkit.entity.Player;

public interface ParticleSpawner {
    Object getData(Color color);

    void spawnParticle(Player player, double x, double y, double z, Object data);

    interface Provider extends FeatureProvider<ParticleSpawner> {
    }
}
