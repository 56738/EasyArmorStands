package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ArmorStandMenu extends EntityMenu<ArmorStand> {
    public ArmorStandMenu(Session session, ArmorStand entity) {
        super(session, entity);
    }

    private static int index(int row, int column) {
        return row * 9 + column;
    }

    @Override
    public void initialize() {
        Player player = getSession().getPlayer();
        ArmorStandRootNode root = getSession().findNode(ArmorStandRootNode.class);
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
        if (player.hasPermission("easyarmorstands.color")) {
            addShortcut(new ColorPickerSlot(this));
        }
        super.initialize();
    }

    @Override
    public void addShortcut(InventorySlot slot) {
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

    @Override
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
}
