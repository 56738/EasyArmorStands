package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EquipmentPropertySlot extends ItemPropertySlot {
    private final EquipmentSlot slot;

    public EquipmentPropertySlot(Property<ItemStack> property, EquipmentSlot slot) {
        super(property);
        this.slot = slot;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }
}
