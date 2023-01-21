package gg.bundlegroup.easyarmorstands.bukkit.feature;

import org.bukkit.entity.ArmorStand;

public interface ArmorStandCanTickAccessor {
    boolean canTick(ArmorStand armorStand);

    void setCanTick(ArmorStand armorStand, boolean canTick);

    interface Provider extends FeatureProvider<ArmorStandCanTickAccessor> {
    }
}
