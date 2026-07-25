package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedAdapter;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import net.minecraft.world.entity.Display;

public interface ModdedItemDisplay extends ItemDisplay, ModdedDisplay {
    @Override
    Display.ItemDisplay getNative();

    static ModdedItemDisplay fromNative(ModdedPlatform platform, Display.ItemDisplay entity) {
        return new ModdedItemDisplayImpl(platform, entity);
    }

    static Display.ItemDisplay toNative(ItemDisplay entity) {
        return ((ModdedItemDisplay) entity).getNative();
    }

    @Override
    default ItemStack getItemStack() {
        return ModdedItemStack.fromNative(getPlatform(), getNative().getItemStack());
    }

    @Override
    default void setItemStack(ItemStack item) {
        getNative().setItemStack(ModdedItemStack.toNative(item));
    }

    @Override
    default ItemDisplayTransform getItemDisplayTransform() {
        return ModdedAdapter.fromNative(getNative().getItemTransform());
    }

    @Override
    default void setItemDisplayTransform(ItemDisplayTransform transform) {
        getNative().setItemTransform(ModdedAdapter.toNative(transform));
    }
}
