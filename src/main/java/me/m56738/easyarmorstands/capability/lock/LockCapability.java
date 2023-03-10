package me.m56738.easyarmorstands.capability.lock;

import org.bukkit.entity.ArmorStand;

public interface LockCapability {
    boolean isLocked(ArmorStand armorStand);

    void setLocked(ArmorStand armorStand, boolean locked);
}
