package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

public interface EntitySpawner {
    <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure);

    interface Provider extends FeatureProvider<EntitySpawner> {
    }
}
