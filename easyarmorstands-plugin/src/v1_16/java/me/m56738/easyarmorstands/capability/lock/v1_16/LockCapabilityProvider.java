package me.m56738.easyarmorstands.capability.lock.v1_16;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

public class LockCapabilityProvider implements CapabilityProvider<LockCapability> {
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
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public LockCapability create(Plugin plugin) {
        return new LockCapabilityImpl();
    }

    private static class LockCapabilityImpl implements LockCapability {
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
    }
}
