package gg.bundlegroup.easyarmorstands.bukkit.feature;

import gg.bundlegroup.easyarmorstands.core.platform.EasMaterial;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface ItemProvider {
    ItemStack createItem(EasMaterial material);

    interface Provider extends FeatureProvider<ItemProvider> {
    }

    class Fallback implements ItemProvider, Provider {
        @SuppressWarnings("deprecation")
        @Override
        public ItemStack createItem(EasMaterial material) {
            switch (material) {
                case BONE_MEAL:
                    return new ItemStack(Material.INK_SACK, 1, (short) 15);
                case BOOK:
                    return new ItemStack(Material.BOOK);
                case BUCKET:
                    return new ItemStack(Material.BUCKET);
                case FEATHER:
                    return new ItemStack(Material.FEATHER);
                case GLOWSTONE_DUST:
                    return new ItemStack(Material.GLOWSTONE_DUST);
                case GOLDEN_APPLE:
                    return new ItemStack(Material.GOLDEN_APPLE);
                case INVISIBILITY_POTION: {
                    ItemStack item = new ItemStack(Material.POTION);
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    meta.addCustomEffect(
                            new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1), true);
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    item.setItemMeta(meta);
                    return item;
                }
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
                case PLAYER_HEAD:
                    return new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                case STICK:
                    return new ItemStack(Material.STICK);
                case STONE_SLAB:
                    return new ItemStack(Material.STEP);
            }
            throw new IllegalArgumentException();
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
