package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.kyori.adventure.text.Component;
import net.minecraft.world.Container;

record ModdedInventoryImpl(
        ModdedPlatform platform,
        InventoryHolder holder,
        Component title,
        Container container) implements ModdedInventory {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public InventoryHolder getHolder() {
        return holder;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public Container getNative() {
        return container;
    }
}
