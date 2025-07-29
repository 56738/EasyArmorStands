package me.m56738.easyarmorstands.paper.api.platform.inventory;

import me.m56738.easyarmorstands.api.platform.inventory.Item;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jspecify.annotations.Nullable;

public interface PaperItem extends Item {
    static PaperItem fromNative(@Nullable ItemStack nativeItem) {
        if (nativeItem == null) {
            return new PaperItemImpl(ItemStack.empty());
        }
        return new PaperItemImpl(nativeItem.clone());
    }

    static ItemStack toNative(@Nullable Item item) {
        if (item == null) {
            return ItemStack.empty();
        }
        return ((PaperItem) item).getNative();
    }

    static PaperItem empty() {
        return PaperItemImpl.EMPTY;
    }

    ItemStack getNative();

    @Override
    default Component displayName() {
        return getNative().displayName();
    }

    @Override
    default boolean isEmpty() {
        return getNative().isEmpty();
    }

    @Override
    default boolean isSkull() {
        return getNative().getItemMeta() instanceof SkullMeta;
    }
}
