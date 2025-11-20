package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EntityEquipmentProperty implements Property<ItemStack> {
    private final EntityEquipment equipment;
    private final EquipmentSlot slot;
    private final PropertyType<ItemStack> type;
    private final EquipmentCapability equipmentCapability;

    public EntityEquipmentProperty(EntityEquipment equipment, EquipmentSlot slot, EquipmentCapability equipmentCapability) {
        this.equipment = equipment;
        this.slot = slot;
        this.type = EntityPropertyTypes.EQUIPMENT.get(slot);
        this.equipmentCapability = equipmentCapability;
    }

    @Override
    public @NotNull PropertyType<ItemStack> getType() {
        return type;
    }

    @Override
    public @NotNull ItemStack getValue() {
        return Util.wrapItem(equipmentCapability.getItem(equipment, slot));
    }

    @Override
    public boolean setValue(@NotNull ItemStack value) {
        equipmentCapability.setItem(equipment, slot, value);
        return true;
    }
}
