package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.invulnerability.InvulnerabilityCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.event.SessionMenuInitializeEvent;
import me.m56738.easyarmorstands.inventory.DisabledSlot;
import me.m56738.easyarmorstands.inventory.InventoryMenu;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class ArmorStandMenu extends InventoryMenu {
    private final Session session;
    private final ArmorStand entity;

    public ArmorStandMenu(Session session, ArmorStand entity) {
        super(6, "EasyArmorStands");
        this.session = session;
        this.entity = entity;
        initialize();
    }

    private static int index(int row, int column) {
        return row * 9 + column;
    }

    private void initialize() {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        Player player = session.getPlayer();
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
        ArmorStandRootNode root = session.findNode(ArmorStandRootNode.class);
        if (root != null) {
            setSlot(3, 7, new SelectNodeSlot(this,
                    root.getPartButton(ArmorStandPart.HEAD),
                    ItemType.PLAYER_HEAD,
                    Component.text("head")));
            setSlot(4, 6, new SelectNodeSlot(this,
                    root.getPartButton(ArmorStandPart.LEFT_ARM),
                    ItemType.LEVER,
                    Component.text("left arm")));
            setSlot(4, 7, new SelectNodeSlot(this,
                    root.getPartButton(ArmorStandPart.BODY),
                    ItemType.LEATHER_CHESTPLATE,
                    Component.text("body")));
            setSlot(4, 8, new SelectNodeSlot(this,
                    root.getPartButton(ArmorStandPart.RIGHT_ARM),
                    ItemType.LEVER,
                    Component.text("right arm")));
            setSlot(5, 6, new SelectNodeSlot(this,
                    root.getPartButton(ArmorStandPart.LEFT_LEG),
                    ItemType.LEVER,
                    Component.text("left leg")));
            setSlot(5, 7, new SelectNodeSlot(this,
                    root.getPositionButton(),
                    ItemType.BUCKET,
                    Component.text("position")));
            setSlot(5, 8, new SelectNodeSlot(this,
                    root.getPartButton(ArmorStandPart.RIGHT_LEG),
                    ItemType.LEVER,
                    Component.text("right leg")));
        }
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
        if (player.hasPermission("easyarmorstands.property.marker")) {
            addButton(new ToggleMarkerSlot(this));
        }
        if (player.hasPermission("easyarmorstands.color")) {
            addEquipmentButton(new ColorPickerSlot(this));
        }
        Bukkit.getPluginManager().callEvent(new SessionMenuInitializeEvent(this));
        setEmptySlots(new DisabledSlot(this, ItemType.LIGHT_BLUE_STAINED_GLASS_PANE));
    }

    public void addEquipmentButton(InventorySlot slot) {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 3; column++) {
                if (slots[index(row, column)] == null) {
                    setSlot(row, column, slot);
                    return;
                }
            }
        }
        throw new IllegalStateException("No space left in the menu");
    }

    public void addButton(InventorySlot slot) {
        for (int row = 0; row <= 1; row++) {
            for (int column = 8; column >= 4; column--) {
                if (slots[index(row, column)] == null) {
                    setSlot(row, column, slot);
                    return;
                }
            }
        }
        for (int column = 4; column <= 5; column++) {
            if (slots[index(2, column)] == null) {
                setSlot(2, column, slot);
                return;
            }
        }
        for (int row = 3; row <= 5; row++) {
            if (slots[index(row, 4)] == null) {
                setSlot(row, 4, slot);
                return;
            }
        }
        throw new IllegalStateException("No space left in the menu");
    }

    public Session getSession() {
        return session;
    }

    public ArmorStand getEntity() {
        return entity;
    }
}
