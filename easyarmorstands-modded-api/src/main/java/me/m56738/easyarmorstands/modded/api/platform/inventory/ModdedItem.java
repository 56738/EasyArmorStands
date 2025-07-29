package me.m56738.easyarmorstands.modded.api.platform.inventory;

import me.m56738.easyarmorstands.api.platform.inventory.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

public interface ModdedItem extends Item {
    static ItemStack toNative(Item item) {
        return ((ModdedItem) item).getNative();
    }

    ItemStack getNative();

    @Override
    default boolean isEmpty() {
        return getNative().isEmpty();
    }

    @Override
    default boolean isSkull() {
        return getNative().is(ItemTags.SKULLS);
    }
}
