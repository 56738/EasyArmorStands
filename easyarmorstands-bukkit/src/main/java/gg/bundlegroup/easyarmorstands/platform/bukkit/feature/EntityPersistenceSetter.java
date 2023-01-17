package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import org.bukkit.entity.Entity;

public interface EntityPersistenceSetter {
    void setPersistent(Entity entity, boolean persistent);

    interface Provider extends FeatureProvider<EntityPersistenceSetter> {
    }
}
