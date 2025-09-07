package me.m56738.easyarmorstands.paper.platform.inventory;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.bukkit.inventory.ItemStack;

public record PaperItemImpl(PaperPlatform platform, ItemStack nativeItem) implements PaperItem {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public ItemStack getNative() {
        return nativeItem.clone();
    }
}
