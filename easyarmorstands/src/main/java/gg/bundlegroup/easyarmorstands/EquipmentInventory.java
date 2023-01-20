package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.platform.EasInventoryListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import net.kyori.adventure.text.Component;

public class EquipmentInventory implements EasInventoryListener {
    private final EasArmorStand armorStand;
    private final EasInventory inventory;
    private final EasArmorEntity.Slot[] slots;

    public EquipmentInventory(EasArmorStand armorStand, EasPlatform platform, Component title) {
        this.armorStand = armorStand;
        this.inventory = platform.createInventory(title, 9, 4, this);
        this.slots = new EasArmorEntity.Slot[36];
        this.slots[4] = EasArmorEntity.Slot.HEAD;
        if (platform.hasSlot(EasArmorEntity.Slot.OFF_HAND)) {
            this.slots[12] = EasArmorEntity.Slot.OFF_HAND;
        }
        this.slots[13] = EasArmorEntity.Slot.BODY;
        this.slots[14] = EasArmorEntity.Slot.MAIN_HAND;
        this.slots[22] = EasArmorEntity.Slot.LEGS;
        this.slots[31] = EasArmorEntity.Slot.FEET;
        for (int i = 0; i < slots.length; i++) {
            EasArmorEntity.Slot slot = slots[i];
            if (slot != null) {
                inventory.setItem(i, armorStand.getItem(slot));
            }
        }
    }

    @Override
    public boolean onClick(int slot) {
        return slots[slot] == null;
    }

    @Override
    public void update() {
        for (int i = 0; i < slots.length; i++) {
            EasArmorEntity.Slot slot = slots[i];
            if (slot != null) {
                armorStand.setItem(slot, inventory.getItem(i));
            }
        }
    }

    public EasInventory getInventory() {
        return inventory;
    }
}
