package me.m56738.easyarmorstands.capability.mannequin;

import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.element.MannequinElementType;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Capability(name = "Mannequin", optional = true)
public interface MannequinCapability {
    boolean isMannequin(Entity entity);

    MannequinElementType<?> createElementType();

    void registerProperties(Entity entity, PropertyRegistry registry);

    ItemStack createProfileItem(Profile profile);

    @Nullable Profile getItemProfile(ItemStack item);
}
