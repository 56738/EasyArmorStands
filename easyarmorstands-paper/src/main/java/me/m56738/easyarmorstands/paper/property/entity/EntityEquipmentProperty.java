package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

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
    public PropertyType<ItemStack> getType() {
        return type;
    }

    @Override
    public ItemStack getValue() {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            return equipment.getItem(slot);
        } else {
            return ItemStack.empty();
        }
    }

    @Override
    public boolean setValue(ItemStack value) {
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
