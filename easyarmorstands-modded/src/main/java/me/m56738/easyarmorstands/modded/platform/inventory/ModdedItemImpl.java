package me.m56738.easyarmorstands.modded.platform.inventory;

import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import me.m56738.easyarmorstands.modded.platform.ModdedPlatformImpl;
import net.kyori.adventure.text.Component;
import net.minecraft.world.item.ItemStack;

public record ModdedItemImpl(ModdedPlatformImpl platform, ItemStack nativeItem) implements ModdedItem {
    @Override
    public ItemStack getNative() {
        return nativeItem;
    }

    @Override
    public Component displayName() {
        return platform.getAdventure().asAdventure(nativeItem.getDisplayName());
    }
}
