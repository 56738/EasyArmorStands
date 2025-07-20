package me.m56738.easyarmorstands.paper.api.platform.item;

import me.m56738.easyarmorstands.api.platform.item.Item;
import org.bukkit.inventory.ItemStack;

public interface PaperItem extends Item {
    static PaperItem fromNative(ItemStack nativeItem) {
        if (nativeItem == null) {
            return new PaperItemImpl(ItemStack.empty());
        }
        return new PaperItemImpl(nativeItem.clone());
    }

    static ItemStack toNative(Item item) {
        return ((PaperItem) item).getNative();
    }

    ItemStack getNative();
}
