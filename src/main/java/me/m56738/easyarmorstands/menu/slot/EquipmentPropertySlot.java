package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;

public class EquipmentPropertySlot extends ItemPropertySlot {
    private final EquipmentSlot slot;

    public EquipmentPropertySlot(EasyArmorStandsCommon eas, Property<ItemStack> property, EquipmentSlot slot) {
        super(eas, property);
        this.slot = slot;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }
}
