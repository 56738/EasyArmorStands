package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.item.ItemStack;

record ModdedItemStackImpl(ModdedPlatform platform, ItemStack item) implements ModdedItemStack {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ItemStack getNative() {
        return item;
    }
}
