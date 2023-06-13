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
        String permission = property.getPermission();
        if (permission != null && !menu.getSession().getPlayer().hasPermission(permission)) {
            return false;
        }
        if (take) {
            ItemStack item = menu.getInventory().getItem(slot);
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            if (!item.equals(property.getValue(menu.getEntity()))) {
                // Prevent taking the item if the inventory view is outdated
                return false;
            }
            if (!menu.getSession().tryChange(menu.getEntity(), property, new ItemStack(Material.AIR))) {
                // Prevent taking the item if the property change is refused
                return false;
            }
            // Don't commit here, will be committed in the task below
        }
        menu.queueTask(() -> {
            ItemStack item = menu.getInventory().getItem(slot);
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            menu.getSession().tryChange(menu.getEntity(), property, item);
            menu.getSession().commit();
        });
        return true;
    }
}
