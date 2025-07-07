package me.m56738.easyarmorstands.api.menu.layout;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import org.bukkit.inventory.EquipmentSlot;

/**
 * A menu layout with equipment (armor) slots on the left and other buttons on the right.
 */
public interface EquipmentMenuLayout extends MenuLayout {
    boolean isEquipmentSlotSupported(EquipmentSlot equipmentSlot);

    void setEquipmentSlot(EquipmentSlot equipmentSlot, MenuSlot slot);
}
