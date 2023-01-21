package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_14;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStandsPlugin;
import gg.bundlegroup.easyarmorstands.bukkit.feature.ToolChecker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ToolCheckerImpl implements ToolChecker {
    private final NamespacedKey key = new NamespacedKey(EasyArmorStandsPlugin.getInstance(), "tool");

    @Override
    public boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    @Override
    public ItemStack createTool() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.RESET + "EasyArmorStands");
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public static class Provider implements ToolChecker.Provider {
        @Override
        public boolean isSupported() {
            try {
                Class.forName("org.bukkit.persistence.PersistentDataContainer");
                return true;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        @Override
        public ToolChecker create() {
            return new ToolCheckerImpl();
        }
    }
}
