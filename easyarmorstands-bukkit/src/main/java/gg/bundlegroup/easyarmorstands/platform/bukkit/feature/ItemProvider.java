package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface ItemProvider {
    ItemStack createPlaceholder();

    interface Provider extends FeatureProvider<ItemProvider> {
    }

    class Fallback implements ItemProvider, Provider {
        @SuppressWarnings("deprecation")
        @Override
        public ItemStack createPlaceholder() {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.RESET.toString());
            item.setItemMeta(meta);
            return item;
        }

        @Override
        public boolean isSupported() {
            try {
                Material.valueOf("STAINED_GLASS_PANE");
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        @Override
        public Priority getPriority() {
            return Priority.FALLBACK;
        }

        @Override
        public ItemProvider create() {
            return this;
        }
    }
}
