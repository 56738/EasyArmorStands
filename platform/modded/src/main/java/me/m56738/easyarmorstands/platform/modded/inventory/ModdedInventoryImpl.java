package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.Container;

record ModdedInventoryImpl(ModdedPlatform platform, Container container) implements ModdedInventory {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Container getNative() {
        return container;
    }
}
