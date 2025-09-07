package me.m56738.easyarmorstands.modded.platform.inventory;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import net.minecraft.world.item.ItemStack;

public record ModdedItemImpl(ModdedPlatformImpl platform, ItemStack nativeItem) implements ModdedItem {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ItemStack getNative() {
        return nativeItem;
    }
}
