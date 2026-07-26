package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.kyori.adventure.text.Component;
import net.minecraft.world.Container;
import org.jspecify.annotations.Nullable;

public interface ModdedInventory extends Inventory, ModdedPlatformHolder {
    Component getTitle();

    Container getNative();

    static Container toNative(Inventory inventory) {
        return ((ModdedInventory) inventory).getNative();
    }

    @Override
    default @Nullable InventoryHolder getHolder() {
        return null;
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
    default void setItem(int i, @Nullable ItemStack item) {
        getNative().setItem(i, ModdedItemStack.toNative(item));
    }

    @Override
    default void clear(int i) {
        getNative().setItem(i, net.minecraft.world.item.ItemStack.EMPTY);
    }
}
