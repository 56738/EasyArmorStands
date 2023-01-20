package gg.bundlegroup.easyarmorstands.platform.bukkit.v1_9;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EquipmentAccessor;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EquipmentAccessorImpl implements EquipmentAccessor {
    @Override
    public ItemStack getItem(EntityEquipment equipment, EasArmorStand.Slot slot) {
        switch (slot) {
            case HEAD:
                return equipment.getHelmet();
            case BODY:
                return equipment.getChestplate();
            case LEFT_HAND:
                return equipment.getItemInOffHand();
            case RIGHT_HAND:
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
    public void setItem(EntityEquipment equipment, EasArmorStand.Slot slot, ItemStack item) {
        switch (slot) {
            case HEAD:
                equipment.setHelmet(item);
                break;
            case BODY:
                equipment.setChestplate(item);
                break;
            case LEFT_HAND:
                equipment.setItemInOffHand(item);
                break;
            case RIGHT_HAND:
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

    public static class Provider implements EquipmentAccessor.Provider {
        @SuppressWarnings("ConstantValue")
        @Override
        public boolean isSupported() {
            return EquipmentSlot.valueOf("OFF_HAND") != null;
        }

        @Override
        public EquipmentAccessor create() {
            return new EquipmentAccessorImpl();
        }
    }
}
