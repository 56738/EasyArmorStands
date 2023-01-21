package gg.bundlegroup.easyarmorstands.bukkit.feature.v1_9;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EquipmentAccessor;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EquipmentAccessorImpl implements EquipmentAccessor {
    @Override
    public ItemStack getItem(EntityEquipment equipment, EasArmorEntity.Slot slot) {
        switch (slot) {
            case HEAD:
                return equipment.getHelmet();
            case BODY:
                return equipment.getChestplate();
            case OFF_HAND:
                return equipment.getItemInOffHand();
            case MAIN_HAND:
                return equipment.getItemInMainHand();
            case LEGS:
                return equipment.getLeggings();
            case FEET:
                return equipment.getBoots();
            default:
                return null;
        }
    }

    @Override
    public void setItem(EntityEquipment equipment, EasArmorEntity.Slot slot, ItemStack item) {
        switch (slot) {
            case HEAD:
                equipment.setHelmet(item);
                break;
            case BODY:
                equipment.setChestplate(item);
                break;
            case OFF_HAND:
                equipment.setItemInOffHand(item);
                break;
            case MAIN_HAND:
                equipment.setItemInMainHand(item);
                break;
            case LEGS:
                equipment.setLeggings(item);
                break;
            case FEET:
                equipment.setBoots(item);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean hasSlot(EasArmorEntity.Slot slot) {
        return true;
    }

    public static class Provider implements EquipmentAccessor.Provider {
        @Override
        public boolean isSupported() {
            try {
                EquipmentSlot.valueOf("OFF_HAND");
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        @Override
        public EquipmentAccessor create() {
            return new EquipmentAccessorImpl();
        }
    }
}
