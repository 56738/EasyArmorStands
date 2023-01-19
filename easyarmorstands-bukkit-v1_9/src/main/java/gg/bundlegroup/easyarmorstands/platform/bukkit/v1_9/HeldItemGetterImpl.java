package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_9;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.HeldItemGetter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HeldItemGetterImpl implements HeldItemGetter {
    @Override
    public ItemStack[] getHeldItems(Player player) {
        PlayerInventory inventory = player.getInventory();
        return new ItemStack[]{
                inventory.getItemInMainHand(),
                inventory.getItemInOffHand()
        };
    }

    public static class Provider implements HeldItemGetter.Provider {
        @Override
        public boolean isSupported() {
            try {
                PlayerInventory.class.getDeclaredMethod("getItemInMainHand");
                PlayerInventory.class.getDeclaredMethod("getItemInOffHand");
                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }

        @Override
        public HeldItemGetter create() {
            return new HeldItemGetterImpl();
        }
    }
}
