package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.item.Item;

record ModdedItemTypeImpl(ModdedPlatform platform, Item item) implements ModdedItemType {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Item getNative() {
        return item;
    }
}
