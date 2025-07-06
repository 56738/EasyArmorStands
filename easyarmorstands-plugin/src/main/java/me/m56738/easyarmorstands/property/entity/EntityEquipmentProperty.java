package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EntityEquipmentProperty implements Property<ItemStack> {
    private final LivingEntity entity;
    private final EquipmentSlot slot;
    private final PropertyType<ItemStack> type;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot) {
        this.entity = entity;
        this.slot = slot;
        this.type = EntityPropertyTypes.EQUIPMENT.get(slot);
    }

    @Override
    public @NotNull PropertyType<ItemStack> getType() {
        return type;
    }

    @Override
    public @NotNull ItemStack getValue() {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            return equipment.getItem(slot);
        } else {
            return Util.getEmptyItem();
        }
    }

    @Override
    public boolean setValue(@NotNull ItemStack value) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            equipment.setItem(slot, value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
