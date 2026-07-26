package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.InventoryFactory;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.kyori.adventure.text.Component;
import net.minecraft.world.SimpleContainer;

public class ModdedInventoryFactory implements InventoryFactory {
    private final ModdedPlatform platform;

    public ModdedInventoryFactory(ModdedPlatform platform) {
        this.platform = platform;
    }

    @Override
    public Inventory createInventory(InventoryHolder holder, Component title, int size) {
        SimpleContainer container = new SimpleContainer(size);
        return new ModdedInventoryImpl(platform, holder, title, container);
    }
}
