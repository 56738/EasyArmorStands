package me.m56738.easyarmorstands.modded.platform.inventory;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedInventory;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;

public record ModdedInventoryImpl(ModdedPlatform platform, Container container) implements ModdedInventory {
    public static ModdedInventoryImpl create(ModdedPlatformImpl platform, int rows) {
        return new ModdedInventoryImpl(platform, new SimpleContainer(rows * 9));
    }

    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Container getNative() {
        return container;
    }
}
