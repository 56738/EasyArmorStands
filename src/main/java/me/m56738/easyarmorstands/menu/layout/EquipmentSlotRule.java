package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.menu.slot.EquipmentPropertySlot;
import me.m56738.easyarmorstands.menu.slot.MenuButtonSlot;
import org.bukkit.inventory.EquipmentSlot;

record EquipmentSlotRule(EquipmentSlot slot) implements MenuLayoutRule {
    @Override
    public boolean matches(MenuButton button) {
        return MenuButtonSlot.toSlot(button) instanceof EquipmentPropertySlot equipmentPropertySlot
                && equipmentPropertySlot.getSlot().equals(slot);
    }
}
