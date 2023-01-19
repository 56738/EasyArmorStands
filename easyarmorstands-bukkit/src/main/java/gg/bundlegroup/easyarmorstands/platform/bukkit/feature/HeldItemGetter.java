package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface HeldItemGetter {
    ItemStack[] getHeldItems(Player player);

    interface Provider extends FeatureProvider<HeldItemGetter> {
    }

    class Fallback implements HeldItemGetter, Provider {
        @Override
        public ItemStack[] getHeldItems(Player player) {
            return new ItemStack[]{player.getItemInHand()};
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
        public HeldItemGetter create() {
            return this;
        }
    }
}
