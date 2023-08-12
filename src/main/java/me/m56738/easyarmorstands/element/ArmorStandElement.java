package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.SelectNodeSlot;
import me.m56738.easyarmorstands.node.ArmorStandButton;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class ArmorStandElement extends SimpleEntityElement<ArmorStand> {
    private final ArmorStand entity;

    public ArmorStandElement(ArmorStand entity, SimpleEntityElementType<ArmorStand> type) {
        super(entity, type);
        this.entity = entity;
    }

    @Override
    public Button createButton(Session session) {
        return new ArmorStandButton(session, entity);
    }

    @Override
    public ElementNode createNode(Session session) {
        return new ArmorStandRootNode(session, entity, this);
    }

    @Override
    protected void populateMenu(Player player, SplitMenuBuilder builder) {
        super.populateMenu(player, builder);

        Session session = EasyArmorStands.getInstance().getSessionManager().getSession(player);
        ArmorStandRootNode root = null;
        if (session != null) {
            root = session.findNode(ArmorStandRootNode.class);
        }
        if (root != null) {
            builder.setSlot(
                    Menu.index(3, 7),
                    new SelectNodeSlot(
                            session,
                            root.getPartButton(ArmorStandPart.HEAD),
                            ItemType.PLAYER_HEAD,
                            Component.text("head")));
            builder.setSlot(
                    Menu.index(4, 6),
                    new SelectNodeSlot(
                            session,
                            root.getPartButton(ArmorStandPart.LEFT_ARM),
                            ItemType.LEVER,
                            Component.text("left arm")));
            builder.setSlot(
                    Menu.index(4, 7),
                    new SelectNodeSlot(
                            session,
                            root.getPartButton(ArmorStandPart.BODY),
                            ItemType.LEATHER_CHESTPLATE,
                            Component.text("body")));
            builder.setSlot(
                    Menu.index(4, 8),
                    new SelectNodeSlot(
                            session,
                            root.getPartButton(ArmorStandPart.RIGHT_ARM),
                            ItemType.LEVER,
                            Component.text("right arm")));
            builder.setSlot(
                    Menu.index(5, 6),
                    new SelectNodeSlot(
                            session,
                            root.getPartButton(ArmorStandPart.LEFT_LEG),
                            ItemType.LEVER,
                            Component.text("left leg")));
            builder.setSlot(
                    Menu.index(5, 7),
                    new SelectNodeSlot(
                            session,
                            root.getPositionButton(),
                            ItemType.BUCKET,
                            Component.text("position")));
            builder.setSlot(
                    Menu.index(5, 8),
                    new SelectNodeSlot(
                            session,
                            root.getPartButton(ArmorStandPart.RIGHT_LEG),
                            ItemType.LEVER,
                            Component.text("right leg")));
        }
    }
}
