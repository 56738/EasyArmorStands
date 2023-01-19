package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface ToolChecker {
    boolean isTool(ItemStack item);

    ItemStack createTool();

    interface Provider extends FeatureProvider<ToolChecker> {
    }

    class Fallback implements ToolChecker, Provider {
        @Override
        public boolean isTool(ItemStack item) {
            return item != null && item.getType() == Material.BLAZE_ROD;
        }

        @Override
        public ItemStack createTool() {
            return new ItemStack(Material.BLAZE_ROD);
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public Priority getPriority() {
            return Priority.FALLBACK;
        }

        @Override
        public ToolChecker create() {
            return this;
        }
    }
}
