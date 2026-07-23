package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.ItemFrame;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;

public interface PaperItemFrame extends ItemFrame, PaperEntity {
    static PaperItemFrame fromNative(org.bukkit.entity.ItemFrame entity) {
        return new PaperItemFrameImpl(entity);
    }

    org.bukkit.entity.ItemFrame getNative();

    static org.bukkit.entity.ItemFrame toNative(ItemFrame entity) {
        return ((PaperItemFrame) entity).getNative();
    }

    @Override
    default ItemStack getItem() {
        return PaperItemStack.fromNative(getNative().getItem());
    }

    @Override
    default void setItem(ItemStack item) {
        getNative().setItem(PaperItemStack.toNative(item));
    }

    @Override
    default boolean isFixed() {
        return getNative().isFixed();
    }

    @Override
    default void setFixed(boolean fixed) {
        getNative().setFixed(fixed);
    }

    @Override
    default boolean isVisible() {
        return getNative().isVisible();
    }

    @Override
    default void setVisible(boolean visible) {
        getNative().setVisible(visible);
    }
}
