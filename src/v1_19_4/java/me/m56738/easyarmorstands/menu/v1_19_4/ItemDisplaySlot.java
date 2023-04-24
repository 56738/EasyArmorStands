package me.m56738.easyarmorstands.menu.v1_19_4;

import me.m56738.easyarmorstands.inventory.InventorySlot;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class ItemDisplaySlot implements InventorySlot {
    private final ItemDisplayMenu menu;
    private final Inventory inventory;
    private final ItemDisplay entity;

    public ItemDisplaySlot(ItemDisplayMenu menu, ItemDisplay entity) {
        this.menu = menu;
        this.inventory = menu.getInventory();
        this.entity = entity;
    }

    @Override
    public void initialize(int slot) {
        inventory.setItem(slot, entity.getItemStack());
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        menu.queueTask(() -> {
            entity.setItemStack(inventory.getItem(slot));
            menu.getSession().commit();
        });
        return true;
    }
}
