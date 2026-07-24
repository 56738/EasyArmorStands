package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.minecraft.world.Container;

public interface ModdedInventory extends Inventory, ModdedPlatformHolder {
    Container getNative();

    static ModdedInventory fromNative(ModdedPlatform platform, Container container) {
        return new ModdedInventoryImpl(platform, container);
    }

    static Container toNative(Inventory inventory) {
        return ((ModdedInventory) inventory).getNative();
    }

    @Override
    default int getSize() {
        return getNative().getContainerSize();
    }

    @Override
    default ItemStack getItem(int i) {
        return ModdedItemStack.fromNative(getPlatform(), getNative().getItem(i));
    }

    @Override
    default void setItem(int i, ItemStack item) {
        getNative().setItem(i, ModdedItemStack.toNative(item));
    }

    @Override
    default void clear(int i) {
        getNative().setItem(i, net.minecraft.world.item.ItemStack.EMPTY);
    }
}
