package me.m56738.easyarmorstands.bukkit.platform;

import me.m56738.easyarmorstands.core.platform.EasItem;
import org.bukkit.inventory.ItemStack;

public class BukkitItem extends BukkitWrapper<ItemStack> implements EasItem {
    public BukkitItem(BukkitPlatform platform, ItemStack item) {
        super(platform, item);
    }

    @Override
    public boolean isTool() {
        return platform().toolChecker().isTool(get());
    }
}
