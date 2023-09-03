package me.m56738.easyarmorstands.capability.lock;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.ArmorStand;

@Capability(name = "Equipment lock", optional = true)
public interface LockCapability {
    boolean isLocked(ArmorStand armorStand);

    void setLocked(ArmorStand armorStand, boolean locked);
}
