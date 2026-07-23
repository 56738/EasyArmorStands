package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.util.Location;

public interface LivingEntity extends Entity {
    Location eyeLocation();

    boolean hasEquipmentSlot(EquipmentSlot slot);

    ItemStack getEquipment(EquipmentSlot slot);

    void setEquipment(EquipmentSlot slot, ItemStack item);

    boolean hasScaleAttribute();

    double getScaleAttribute();

    void setScaleAttribute(double value);
}
