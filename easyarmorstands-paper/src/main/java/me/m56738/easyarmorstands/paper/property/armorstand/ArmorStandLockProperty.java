package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;

public class ArmorStandLockProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandLockProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.LOCK;
    }

    @Override
    public Boolean getValue() {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            for (ArmorStand.LockType type : ArmorStand.LockType.values()) {
                if (entity.hasEquipmentLock(slot, type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean setValue(Boolean value) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            for (ArmorStand.LockType type : ArmorStand.LockType.values()) {
                if (value) {
                    entity.addEquipmentLock(slot, type);
                } else {
                    entity.removeEquipmentLock(slot, type);
                }
            }
        }
        return true;
    }
}
