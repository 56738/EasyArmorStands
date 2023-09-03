package me.m56738.easyarmorstands.capability.tick;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.ArmorStand;

@Capability(name = "Armor stand ticking", optional = true)
public interface TickCapability {
    boolean canTick(ArmorStand armorStand);

    void setCanTick(ArmorStand armorStand, boolean canTick);
}
