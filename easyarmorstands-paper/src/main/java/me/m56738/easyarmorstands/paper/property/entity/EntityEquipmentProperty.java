package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

public class EntityEquipmentProperty implements Property<Item> {
    private final LivingEntity entity;
    private final org.bukkit.inventory.EquipmentSlot nativeSlot;
    private final PropertyType<Item> type;

    public EntityEquipmentProperty(LivingEntity entity, EquipmentSlot slot) {
        this.entity = entity;
        this.nativeSlot = org.bukkit.inventory.EquipmentSlot.valueOf(slot.name());
        this.type = EntityPropertyTypes.EQUIPMENT.get(slot);
    }

    @Override
    public PropertyType<Item> getType() {
        return type;
    }

    @Override
    public Item getValue() {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            return PaperItem.fromNative(equipment.getItem(nativeSlot));
        } else {
            return PaperItem.empty();
        }
    }

    @Override
    public boolean setValue(Item value) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            equipment.setItem(nativeSlot, PaperItem.toNative(value));
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
