package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_16;

import gg.bundlegroup.easyarmorstands.bukkit.feature.ArmorStandLockAccessor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;

public class ArmorStandLockAccessorImpl implements ArmorStandLockAccessor {
    @Override
    public boolean isLocked(ArmorStand armorStand) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            for (ArmorStand.LockType type : ArmorStand.LockType.values()) {
                if (armorStand.hasEquipmentLock(slot, type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setLocked(ArmorStand armorStand, boolean locked) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            for (ArmorStand.LockType type : ArmorStand.LockType.values()) {
                if (locked) {
                    armorStand.addEquipmentLock(slot, type);
                } else {
                    armorStand.removeEquipmentLock(slot, type);
                }
            }
        }
    }

    public static class Provider implements ArmorStandLockAccessor.Provider {
        @Override
        public boolean isSupported() {
            try {
                ArmorStand.class.getDeclaredMethod("addEquipmentLock", EquipmentSlot.class, ArmorStand.LockType.class);
                return true;
            } catch (Throwable e) {
                return false;
            }
        }

        @Override
        public ArmorStandLockAccessor create() {
            return new ArmorStandLockAccessorImpl();
        }
    }
}
