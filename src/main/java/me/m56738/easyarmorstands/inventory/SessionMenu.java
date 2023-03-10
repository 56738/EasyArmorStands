package me.m56738.easyarmorstands.inventory;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.invulnerability.InvulnerabilityCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.event.SessionMenuInitializeEvent;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SessionMenu implements InventoryListener {
    private final ArmorStandSession session;
    private final Inventory inventory;
    private final InventorySlot[] slots;
    private final List<Runnable> queue = new ArrayList<>();
    private boolean initialized;

    public SessionMenu(ArmorStandSession session) {
        this.session = session;
        this.inventory = Bukkit.createInventory(this, 9 * 6, "EasyArmorStands");
        this.slots = new InventorySlot[9 * 6];
        initialize();
    }

    private static int index(int row, int column) {
        return row * 9 + column;
    }

    private void initialize() {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        Player player = session.getPlayer();
        Arrays.fill(slots, new DisabledSlot(inventory, Util.createItem(ItemType.LIGHT_BLUE_STAINED_GLASS_PANE,
                Component.empty(), Collections.emptyList())));
        if (player.hasPermission("easyarmorstands.property.equipment")) {
            setSlot(2, 1, new EquipmentItemSlot(this, EquipmentSlot.HEAD));
            EquipmentSlot offHand = plugin.getCapability(EquipmentCapability.class).getOffHand();
            if (offHand != null) {
                setSlot(3, 0, new EquipmentItemSlot(this, offHand));
            }
            setSlot(3, 1, new EquipmentItemSlot(this, EquipmentSlot.CHEST));
            setSlot(3, 2, new EquipmentItemSlot(this, EquipmentSlot.HAND));
            setSlot(4, 1, new EquipmentItemSlot(this, EquipmentSlot.LEGS));
            setSlot(5, 1, new EquipmentItemSlot(this, EquipmentSlot.FEET));
        }
        if (player.hasPermission("easyarmorstands.property.arms")) {
            addButton(new ToggleArmsSlot(this));
        }
        if (player.hasPermission("easyarmorstands.property.size")) {
            addButton(new ToggleSizeSlot(this));
        }
        if (player.hasPermission("easyarmorstands.property.baseplate")) {
            addButton(new ToggleBasePlateSlot(this));
        }
        TickCapability tickCapability = plugin.getCapability(TickCapability.class);
        if (player.hasPermission("easyarmorstands.property.gravity")) {
            addButton(new ToggleGravitySlot(this, tickCapability));
        }
        setSlot(3, 7, new SelectBoneSlot(this,
                session.getBones().get("head"),
                ItemType.PLAYER_HEAD,
                Component.text("head")));
        setSlot(4, 6, new SelectBoneSlot(this,
                session.getBones().get("leftarm"),
                ItemType.LEVER,
                Component.text("left arm")));
        setSlot(4, 7, new SelectBoneSlot(this,
                session.getBones().get("body"),
                ItemType.LEATHER_CHESTPLATE,
                Component.text("body")));
        setSlot(4, 8, new SelectBoneSlot(this,
                session.getBones().get("rightarm"),
                ItemType.LEVER,
                Component.text("right arm")));
        setSlot(5, 6, new SelectBoneSlot(this,
                session.getBones().get("leftleg"),
                ItemType.LEVER,
                Component.text("left leg")));
        setSlot(5, 7, new SelectBoneSlot(this,
                session.getBones().get("position"),
                ItemType.BUCKET,
                Component.text("position")));
        setSlot(5, 8, new SelectBoneSlot(this,
                session.getBones().get("rightleg"),
                ItemType.LEVER,
                Component.text("right leg")));
        if (player.hasPermission("easyarmorstands.property.visible")) {
            addButton(new ToggleVisibilitySlot(this));
        }
        LockCapability lockCapability = plugin.getCapability(LockCapability.class);
        if (lockCapability != null && player.hasPermission("easyarmorstands.property.lock")) {
            addButton(new ToggleLockSlot(this, lockCapability));
        }
        GlowCapability glowCapability = plugin.getCapability(GlowCapability.class);
        if (glowCapability != null && player.hasPermission("easyarmorstands.property.glow")) {
            addButton(new ToggleGlowingSlot(this, glowCapability));
        }
        InvulnerabilityCapability invulnerabilityCapability = plugin.getCapability(InvulnerabilityCapability.class);
        if (invulnerabilityCapability != null && player.hasPermission("easyarmorstands.property.invulnerable")) {
            addButton(new ToggleInvulnerabilitySlot(this, invulnerabilityCapability));
        }
        Bukkit.getPluginManager().callEvent(new SessionMenuInitializeEvent(this));
        initialized = true;
        for (int i = 0; i < slots.length; i++) {
            slots[i].initialize(i);
        }
    }

    @Override
    public boolean onClick(int slot, boolean click, boolean put, boolean take, ItemStack cursor) {
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
        for (int row = 0; row <= 1; row++) {
            for (int column = 8; column >= 3; column--) {
                if (slots[index(row, column)] instanceof DisabledSlot) {
                    setSlot(row, column, slot);
                    return;
                }
            }
        }
        for (int column = 4; column <= 5; column++) {
            if (slots[index(2, column)] instanceof DisabledSlot) {
                setSlot(2, column, slot);
                return;
            }
        }
        for (int row = 3; row <= 5; row++) {
            if (slots[index(row, 4)] instanceof DisabledSlot) {
                setSlot(row, 4, slot);
                return;
            }
        }
        throw new IllegalStateException("No space left in the menu");
    }

    public void queueTask(Runnable runnable) {
        queue.add(runnable);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArmorStandSession getSession() {
        return session;
    }
}
