package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;

public interface PaperItemDisplay extends ItemDisplay, PaperDisplay {
    static PaperItemDisplay fromNative(org.bukkit.entity.ItemDisplay entity) {
        return new PaperItemDisplayImpl(entity);
    }

    org.bukkit.entity.ItemDisplay getNative();

    static org.bukkit.entity.ItemDisplay toNative(ItemDisplay entity) {
        return ((PaperItemDisplay) entity).getNative();
    }

    @Override
    default ItemStack getItemStack() {
        return PaperItemStack.fromNative(getNative().getItemStack());
    }

    @Override
    default void setItemStack(ItemStack item) {
        getNative().setItemStack(PaperItemStack.toNative(item));
    }

    @Override
    default ItemDisplayTransform getItemDisplayTransform() {
        return PaperAdapter.fromNative(getNative().getItemDisplayTransform());
    }

    @Override
    default void setItemDisplayTransform(ItemDisplayTransform transform) {
        getNative().setItemDisplayTransform(PaperAdapter.toNative(transform));
    }
}
