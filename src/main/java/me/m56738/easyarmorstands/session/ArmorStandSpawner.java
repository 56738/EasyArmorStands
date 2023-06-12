package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

public class ArmorStandSpawner implements EntitySpawner<ArmorStand> {
    @Override
    public EntityType getEntityType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public ArmorStand spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return spawnCapability.spawnEntity(location, ArmorStand.class, e -> {
            e.setGravity(false);
            for (ArmorStandPart part : ArmorStandPart.values()) {
                part.setPose(e, EulerAngle.ZERO);
            }
        });
    }
}
