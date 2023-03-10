package me.m56738.easyarmorstands.capability.tool.v1_14;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Objects;

public class ToolCapabilityProvider implements CapabilityProvider<ToolCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.persistence.PersistentDataContainer");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public ToolCapability create(Plugin plugin) {
        return new ToolCapabilityImpl(plugin);
    }

    private static class ToolCapabilityImpl implements ToolCapability {
        private final NamespacedKey key;

        public ToolCapabilityImpl(Plugin plugin) {
            key = new NamespacedKey(plugin, "tool");
        }

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
            meta.setDisplayName(ChatColor.GOLD + "EasyArmorStands");
            meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            meta.setLore(Arrays.asList(
                    ChatColor.GRAY + "Right click an armor stand to start editing.",
                    ChatColor.GRAY + "Sneak + right click to spawn an armor stand.",
                    ChatColor.GRAY + "Drop to stop editing."
            ));
            item.setItemMeta(meta);
            return item;
        }
    }
}
