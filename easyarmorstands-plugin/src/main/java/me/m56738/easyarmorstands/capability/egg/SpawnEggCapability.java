package me.m56738.easyarmorstands.capability.egg;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

@Capability(name = "Spawn eggs", optional = true)
public interface SpawnEggCapability {
    ItemStack createSpawnEgg(Entity entity);
}
