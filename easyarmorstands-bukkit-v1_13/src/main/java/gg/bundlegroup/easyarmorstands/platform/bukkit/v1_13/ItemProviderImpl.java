package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_13;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ItemProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemProviderImpl implements ItemProvider {
    @Override
    public ItemStack createPlaceholder() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET.toString());
        item.setItemMeta(meta);
        return item;
    }

    public static class Provider implements ItemProvider.Provider {
        @Override
        public boolean isSupported() {
            try {
                Material.valueOf("RED_STAINED_GLASS_PANE");
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        @Override
        public ItemProvider create() {
            return new ItemProviderImpl();
        }
    }
}
