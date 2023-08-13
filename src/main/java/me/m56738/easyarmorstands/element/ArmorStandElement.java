package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.NodeSlot;
import me.m56738.easyarmorstands.node.ArmorStandButton;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.joml.Quaterniondc;

import java.util.function.Consumer;

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
    protected void populateMenu(Player player, SplitMenuBuilder builder, PropertyContainer container) {
        super.populateMenu(player, builder, container);

        Session session = EasyArmorStands.getInstance().getSessionManager().getSession(player);
        ArmorStandRootNode root = null;
        if (session != null) {
            root = session.findNode(ArmorStandRootNode.class);
        }
        if (root == null || root.getElement().entity != entity) {
            return;
        }

        builder.setSlot(
                Menu.index(3, 7),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.HEAD),
                        getResetAction(ArmorStandPart.HEAD, container),
                        ItemType.PLAYER_HEAD,
                        Component.text("head")));
        builder.setSlot(
                Menu.index(4, 6),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.LEFT_ARM),
                        getResetAction(ArmorStandPart.LEFT_ARM, container),
                        ItemType.LEVER,
                        Component.text("left arm")));
        builder.setSlot(
                Menu.index(4, 7),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.BODY),
                        getResetAction(ArmorStandPart.BODY, container),
                        ItemType.LEATHER_CHESTPLATE,
                        Component.text("body")));
        builder.setSlot(
                Menu.index(4, 8),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.RIGHT_ARM),
                        getResetAction(ArmorStandPart.RIGHT_ARM, container),
                        ItemType.LEVER,
                        Component.text("right arm")));
        builder.setSlot(
                Menu.index(5, 6),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.LEFT_LEG),
                        getResetAction(ArmorStandPart.LEFT_LEG, container),
                        ItemType.LEVER,
                        Component.text("left leg")));
        builder.setSlot(
                Menu.index(5, 7),
                new NodeSlot(
                        session,
                        root.getPositionButton(),
                        new YawResetAction(container.get(EntityLocationProperty.TYPE), container),
                        ItemType.BUCKET,
                        Component.text("position")));
        builder.setSlot(
                Menu.index(5, 8),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.RIGHT_LEG),
                        getResetAction(ArmorStandPart.RIGHT_LEG, container),
                        ItemType.LEVER,
                        Component.text("right leg")));
    }

    private Consumer<MenuClick> getResetAction(ArmorStandPart part, PropertyContainer container) {
        Property<Quaterniondc> property = container.get(ArmorStandPoseProperty.type(part));
        return new ResetAction<>(property, container, Util.IDENTITY);
    }

    private static class ResetAction<T> implements Consumer<MenuClick> {
        private final Property<T> property;
        private final PropertyContainer container;
        private final T value;

        private ResetAction(Property<T> property, PropertyContainer container, T value) {
            this.property = property;
            this.container = container;
            this.value = value;
        }

        @Override
        public void accept(MenuClick click) {
            property.setValue(value);
            container.commit();
        }
    }

    private static class YawResetAction implements Consumer<MenuClick> {
        private final Property<Location> property;
        private final PropertyContainer container;

        private YawResetAction(Property<Location> property, PropertyContainer container) {
            this.property = property;
            this.container = container;
        }

        @Override
        public void accept(MenuClick click) {
            Location location = property.getValue().clone();
            location.setYaw(0);
            location.setPitch(0);
            property.setValue(location);
            container.commit();
        }
    }
}
