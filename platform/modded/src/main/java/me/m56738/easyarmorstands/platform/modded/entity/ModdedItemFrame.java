package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.ItemFrame;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;

public interface ModdedItemFrame extends ItemFrame, ModdedEntity {
    @Override
    net.minecraft.world.entity.decoration.ItemFrame getNative();

    static ModdedItemFrame fromNative(ModdedPlatform platform, net.minecraft.world.entity.decoration.ItemFrame entity) {
        return new ModdedItemFrameImpl(platform, entity);
    }

    static net.minecraft.world.entity.decoration.ItemFrame toNative(ItemFrame entity) {
        return ((ModdedItemFrame) entity).getNative();
    }

    @Override
    default ItemStack getItem() {
        return ModdedItemStack.fromNative(getPlatform(), getNative().getItem());
    }

    @Override
    default void setItem(ItemStack item) {
        getNative().setItem(ModdedItemStack.toNative(item));
    }

    @Override
    default boolean isFixed() {
        return getNative().fixed;
    }

    @Override
    default void setFixed(boolean fixed) {
        getNative().fixed = fixed;
    }

    @Override
    default boolean isVisible() {
        return !getNative().isInvisible();
    }

    @Override
    default void setVisible(boolean visible) {
        getNative().setInvisible(!visible);
    }
}
