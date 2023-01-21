package gg.bundlegroup.easyarmorstands.bukkit.platform;

import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
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
