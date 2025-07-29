package me.m56738.easyarmorstands.modded.api.platform;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import net.minecraft.world.item.ItemStack;

public interface ModdedPlatform extends Platform {
    ModdedItem getItem(ItemStack nativeItem);
}
