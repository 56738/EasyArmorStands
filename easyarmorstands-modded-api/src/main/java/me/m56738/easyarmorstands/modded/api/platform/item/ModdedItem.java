package me.m56738.easyarmorstands.modded.api.platform.item;

import me.m56738.easyarmorstands.api.platform.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ModdedItem extends Item {
    static ModdedItem fromNative(ItemStack nativeItem) {
        return new ModdedItemImpl(nativeItem.copy());
    }

    static ItemStack toNative(Item item) {
        return ((ModdedItem) item).getNative();
    }

    ItemStack getNative();
}
