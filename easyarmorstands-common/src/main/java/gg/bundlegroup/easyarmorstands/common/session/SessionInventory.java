package gg.bundlegroup.easyarmorstands.common.session;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventoryListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import net.kyori.adventure.text.Component;

public class SessionInventory implements EasInventoryListener {
    private final Session session;
    private final EasInventory inventory;
    private final EasArmorEntity.Slot[] slots;

    public SessionInventory(Session session, EasPlatform platform) {
        this.session = session;
        this.inventory = platform.createInventory(
                Component.text("EasyArmorStands"), 9, 6, this);
        this.slots = new EasArmorEntity.Slot[9 * 6];
        this.slots[13] = EasArmorEntity.Slot.HEAD;
        if (platform.hasSlot(EasArmorEntity.Slot.OFF_HAND)) {
            this.slots[21] = EasArmorEntity.Slot.OFF_HAND;
        }
        this.slots[22] = EasArmorEntity.Slot.BODY;
        this.slots[23] = EasArmorEntity.Slot.MAIN_HAND;
        this.slots[31] = EasArmorEntity.Slot.LEGS;
        this.slots[40] = EasArmorEntity.Slot.FEET;
        EasItem placeholder = platform.createPlaceholderItem();
        EasArmorStand armorStand = session.getEntity();
        for (int i = 0; i < slots.length; i++) {
            EasArmorEntity.Slot slot = slots[i];
            if (slot != null) {
                inventory.setItem(i, armorStand.getItem(slot));
            } else {
                inventory.setItem(i, placeholder);
            }
        }
    }

    @Override
    public boolean onClick(int slot) {
        return slots[slot] == null;
    }

    @Override
    public void update() {
        EasArmorStand armorStand = session.getEntity();
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
