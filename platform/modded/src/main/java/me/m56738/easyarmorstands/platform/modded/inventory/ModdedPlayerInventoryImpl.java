package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.player.Inventory;

record ModdedPlayerInventoryImpl(ModdedPlatform platform, Inventory inventory) implements ModdedPlayerInventory {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Inventory getNative() {
        return inventory;
    }
}
