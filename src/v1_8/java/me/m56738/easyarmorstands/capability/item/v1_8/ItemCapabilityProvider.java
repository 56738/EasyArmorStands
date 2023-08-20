package me.m56738.easyarmorstands.capability.item.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ItemCapabilityProvider implements CapabilityProvider<ItemCapability> {
    @Override
    public boolean isSupported() {
        try {
            Material.valueOf("STEP");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public ItemCapability create(Plugin plugin) {
        return new ItemCapabilityImpl();
    }

    private static class ItemCapabilityImpl implements ItemCapability {
        @Override
        @SuppressWarnings("deprecation")
        public ItemStack createItem(ItemType type) {
            switch (type) {
                case ARMOR_STAND:
                    return new ItemStack(Material.ARMOR_STAND);
                case BLAZE_ROD:
                    return new ItemStack(Material.BLAZE_ROD);
                case BUCKET:
                    return new ItemStack(Material.BUCKET);
                case FEATHER:
                    return new ItemStack(Material.FEATHER);
                case GLOWSTONE_DUST:
                    return new ItemStack(Material.GLOWSTONE_DUST);
                case IRON_BARS:
                    return new ItemStack(Material.IRON_FENCE);
                case LEATHER_CHESTPLATE: {
                    ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
                    ItemMeta meta = item.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(meta);
                    return item;
                }
                case LEVER:
                    return new ItemStack(Material.LEVER);
                case LIGHT_BLUE_STAINED_GLASS_PANE:
                    return new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getWoolData());
                case NAME_TAG:
                    return new ItemStack(Material.NAME_TAG);
                case PLAYER_HEAD:
                    return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                case STONE:
                    return new ItemStack(Material.STONE);
                case STONE_SLAB:
                    return new ItemStack(Material.STEP);
                case TNT:
                    return new ItemStack(Material.TNT);
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        @SuppressWarnings("deprecation")
        public ItemStack createColor(DyeColor color) {
            return new ItemStack(Material.WOOL, 1, color.getWoolData());
        }
    }
}
