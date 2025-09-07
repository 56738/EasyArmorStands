package me.m56738.easyarmorstands.modded.api.platform.inventory;

import me.m56738.easyarmorstands.api.platform.inventory.Inventory;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatformHolder;
import net.minecraft.world.Container;

public interface ModdedInventory extends Inventory, ModdedPlatformHolder {
    static Container toNative(Inventory inventory) {
        return ((ModdedInventory) inventory).getNative();
    }

    Container getNative();

    @Override
    default int getSize() {
        return getNative().getContainerSize();
    }

    @Override
    default Item getItem(int slot) {
        return getPlatform().getItem(getNative().getItem(slot));
    }

    @Override
    default void setItem(int slot, Item item) {
        getNative().setItem(slot, ModdedItem.toNative(item));
    }
}
