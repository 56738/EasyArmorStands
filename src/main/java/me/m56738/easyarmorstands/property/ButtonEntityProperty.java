package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.EntityMenu;
import org.bukkit.entity.Entity;

public interface ButtonEntityProperty<E extends Entity, T> extends EntityProperty<E, T> {
    InventorySlot createSlot(EntityMenu<? extends E> menu);

    default int getSlotIndex() {
        return -1;
    }
}
