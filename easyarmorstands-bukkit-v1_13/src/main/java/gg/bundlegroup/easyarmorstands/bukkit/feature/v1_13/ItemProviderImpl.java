package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_13;

import gg.bundlegroup.easyarmorstands.bukkit.feature.ItemProvider;
import gg.bundlegroup.easyarmorstands.core.platform.EasMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ItemProviderImpl implements ItemProvider {
    @Override
    public ItemStack createItem(EasMaterial material) {
        switch (material) {
            case BONE_MEAL:
                return new ItemStack(Material.BONE_MEAL);
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
                meta.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                item.setItemMeta(meta);
                return item;
            }
            case IRON_BARS:
                return new ItemStack(Material.IRON_BARS);
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
                return new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            case PLAYER_HEAD:
                return new ItemStack(Material.PLAYER_HEAD);
            case STICK:
                return new ItemStack(Material.STICK);
            case STONE_SLAB:
                return new ItemStack(Material.STONE_SLAB);
        }
        throw new IllegalArgumentException();
    }

    public static class Provider implements ItemProvider.Provider {
        @Override
        public boolean isSupported() {
            try {
                Material.class.getDeclaredMethod("getMaterial", String.class, boolean.class);
                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        @Override
        public ItemProvider create() {
            return new ItemProviderImpl();
        }
    }
}
