package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.common.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventoryListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SessionMenu implements EasInventoryListener {
    private final Session session;
    private final EasInventory inventory;
    private final InventorySlot[] slots;
    private final List<Runnable> queue = new ArrayList<>();
    private boolean initialized;

    public SessionMenu(Session session, EasPlatform platform) {
        this.session = session;
        this.inventory = platform.createInventory(
                Component.text("EasyArmorStands"), 9, 6, this);
        this.slots = new InventorySlot[9 * 6];
        initialize();
    }

    private static int index(int row, int column) {
        return row * 9 + column;
    }

    private void initialize() {
        EasPlatform platform = inventory.platform();
        Arrays.fill(slots, new DisabledSlot(inventory, platform.createItem(
                EasMaterial.LIGHT_BLUE_STAINED_GLASS_PANE,
                Component.empty(), Collections.emptyList())));
        setSlot(2, 1, new EquipmentItemSlot(this, EasArmorEntity.Slot.HEAD));
        if (platform.hasSlot(EasArmorEntity.Slot.OFF_HAND)) {
            setSlot(3, 0, new EquipmentItemSlot(this, EasArmorEntity.Slot.OFF_HAND));
        }
        setSlot(3, 1, new EquipmentItemSlot(this, EasArmorEntity.Slot.BODY));
        setSlot(3, 2, new EquipmentItemSlot(this, EasArmorEntity.Slot.MAIN_HAND));
        setSlot(4, 1, new EquipmentItemSlot(this, EasArmorEntity.Slot.LEGS));
        setSlot(5, 1, new EquipmentItemSlot(this, EasArmorEntity.Slot.FEET));
        setSlot(3, 4, new ToggleArmsSlot(this));
        setSlot(4, 4, new ToggleSizeSlot(this));
        setSlot(5, 4, new ToggleBasePlateSlot(this));
        setSlot(5, 3, new ToggleGravitySlot(this));
        setSlot(3, 7, new SelectHandleSlot(this,
                session.getHandles().get("head"),
                EasMaterial.PLAYER_HEAD,
                Component.text("head")));
        setSlot(4, 6, new SelectHandleSlot(this,
                session.getHandles().get("leftarm"),
                EasMaterial.LEVER,
                Component.text("left arm")));
        setSlot(4, 7, new SelectHandleSlot(this,
                session.getHandles().get("body"),
                EasMaterial.LEATHER_CHESTPLATE,
                Component.text("body")));
        setSlot(4, 8, new SelectHandleSlot(this,
                session.getHandles().get("rightarm"),
                EasMaterial.LEVER,
                Component.text("right arm")));
        setSlot(5, 6, new SelectHandleSlot(this,
                session.getHandles().get("leftleg"),
                EasMaterial.LEVER,
                Component.text("left leg")));
        setSlot(5, 7, new SelectHandleSlot(this,
                session.getHandles().get("position"),
                EasMaterial.BUCKET,
                Component.text("position")));
        setSlot(5, 8, new SelectHandleSlot(this,
                session.getHandles().get("rightleg"),
                EasMaterial.LEVER,
                Component.text("right leg")));
        addButton(new ToggleVisibilitySlot(this));
        if (platform.hasFeature(EasFeature.ARMOR_STAND_LOCK)) {
            addButton(new ToggleLockSlot(this));
        }
        if (platform.hasFeature(EasFeature.ENTITY_GLOW)) {
            addButton(new ToggleGlowingSlot(this));
        }
        if (platform.hasFeature(EasFeature.ENTITY_INVULNERABLE)) {
            addButton(new ToggleInvulnerabilitySlot(this));
        }
        platform.onInventoryInitialize(this);
        initialized = true;
        for (int i = 0; i < slots.length; i++) {
            slots[i].initialize(i);
        }
    }

    @Override
    public boolean onClick(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        if (slot < 0 || slot >= slots.length) {
            return true;
        }
        return slots[slot].onInteract(slot, click, put, take, cursor);
    }

    @Override
    public void update() {
        for (Runnable runnable : queue) {
            runnable.run();
        }
        queue.clear();
    }

    public void setSlot(int row, int column, InventorySlot slot) {
        int id = index(row, column);
        slots[id] = slot;
        if (initialized) {
            slot.initialize(id);
        }
    }

    public void addEquipmentButton(InventorySlot slot) {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 3; column++) {
                if (slots[index(row, column)] instanceof DisabledSlot) {
                    setSlot(row, column, slot);
                    return;
                }
            }
        }
        throw new IllegalStateException("No space left in the menu");
    }

    public void addButton(InventorySlot slot) {
        for (int row = 0; row < 6; row++) {
            for (int column = 4; column < 9; column++) {
                if (slots[index(row, column)] instanceof DisabledSlot) {
                    setSlot(row, column, slot);
                    return;
                }
            }
        }
        throw new IllegalStateException("No space left in the menu");
    }

    public void queueTask(Runnable runnable) {
        queue.add(runnable);
    }

    public EasInventory getInventory() {
        return inventory;
    }

    public Session getSession() {
        return session;
    }
}
