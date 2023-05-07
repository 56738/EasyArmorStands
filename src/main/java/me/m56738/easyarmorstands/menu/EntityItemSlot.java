package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.property.EntityProperty;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class EntityItemSlot<T extends Entity> implements InventorySlot {
    private final EntityMenu<T> menu;
    private final EntityProperty<? super T, ItemStack> property;

    public EntityItemSlot(EntityMenu<T> menu, EntityProperty<? super T, ItemStack> property) {
        this.menu = menu;
        this.property = property;
    }

    @Override
    public void initialize(int slot) {
        menu.getInventory().setItem(slot, property.getValue(menu.getEntity()));
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        menu.queueTask(() -> {
            ItemStack item = menu.getInventory().getItem(slot);
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            menu.getSession().setProperty(menu.getEntity(), property, item);
            menu.getSession().commit();
        });
        return true;
    }
}
