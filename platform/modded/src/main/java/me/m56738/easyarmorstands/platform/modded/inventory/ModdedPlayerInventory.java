package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.PlayerInventory;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;

import java.util.HashMap;

public interface ModdedPlayerInventory extends PlayerInventory, ModdedInventory {
    Inventory getNative();

    static ModdedPlayerInventory fromNative(ModdedPlatform platform, Inventory inventory) {
        return new ModdedPlayerInventoryImpl(platform, inventory);
    }

    static Inventory toNative(PlayerInventory inventory) {
        return ((ModdedPlayerInventory) inventory).getNative();
    }

    @Override
    default HashMap<Integer, ItemStack> addItem(ItemStack... items) {
        Inventory inventory = getNative();
        HashMap<Integer, ItemStack> remaining = new HashMap<>();
        for (int i = 0; i < items.length; i++) {
            net.minecraft.world.item.ItemStack item = ModdedItemStack.toNative(items[i]);
            if (inventory.add(item) && item.isEmpty()) {
                continue;
            }
            remaining.put(i, ModdedItemStack.fromNative(getPlatform(), item));
        }
        return remaining;
    }

    @Override
    default ItemStack getItemInMainHand() {
        return ModdedItemStack.fromNative(getPlatform(), getNative().player.getItemInHand(InteractionHand.MAIN_HAND));
    }

    @Override
    default ItemStack getItemInOffHand() {
        return ModdedItemStack.fromNative(getPlatform(), getNative().player.getItemInHand(InteractionHand.OFF_HAND));
    }

    @Override
    default void setItemInMainHand(ItemStack item) {
        getNative().player.setItemInHand(InteractionHand.MAIN_HAND, ModdedItemStack.toNative(item));
    }

    @Override
    default void setHeldItemSlot(int i) {
        getNative().setSelectedSlot(i);
    }
}
