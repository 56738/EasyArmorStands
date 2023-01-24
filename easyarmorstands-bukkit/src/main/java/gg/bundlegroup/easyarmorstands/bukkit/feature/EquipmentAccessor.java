package gg.bundlegroup.easyarmorstands.bukkit.feature;

import gg.bundlegroup.easyarmorstands.core.platform.EasArmorEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public interface EquipmentAccessor {
    ItemStack getItem(EntityEquipment equipment, EasArmorEntity.Slot slot);

    void setItem(EntityEquipment equipment, EasArmorEntity.Slot slot, ItemStack item);

    boolean hasSlot(EasArmorEntity.Slot slot);

    interface Provider extends FeatureProvider<EquipmentAccessor> {
    }

    class Fallback implements EquipmentAccessor, Provider {
        @Override
        public ItemStack getItem(EntityEquipment equipment, EasArmorEntity.Slot slot) {
            switch (slot) {
                case HEAD:
                    return equipment.getHelmet();
                case BODY:
                    return equipment.getChestplate();
                case MAIN_HAND:
                    return equipment.getItemInHand();
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
                case MAIN_HAND:
                    equipment.setItemInHand(item);
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
            return slot != EasArmorEntity.Slot.OFF_HAND;
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public Priority getPriority() {
            return Priority.FALLBACK;
        }

        @Override
        public EquipmentAccessor create() {
            return this;
        }
    }
}
