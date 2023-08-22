package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasConfig;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.NodeSlot;
import me.m56738.easyarmorstands.node.ArmorStandButton;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.ElementNode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

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
    protected void populateMenu(EasPlayer player, SplitMenuBuilder builder, PropertyContainer container) {
        super.populateMenu(player, builder, container);

        Session session = player.session();
        ArmorStandRootNode root = null;
        if (session != null) {
            root = session.findNode(ArmorStandRootNode.class);
        }
        if (root == null || root.getElement().entity != entity) {
            return;
        }

        EasConfig config = EasyArmorStands.getInstance().getConfiguration();
        builder.setSlot(
                Menu.index(3, 7),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.HEAD),
                        getResetAction(ArmorStandPart.HEAD, container),
                        config.getArmorStandPartButtonTemplate(ArmorStandPart.HEAD)));
        builder.setSlot(
                Menu.index(4, 6),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.LEFT_ARM),
                        getResetAction(ArmorStandPart.LEFT_ARM, container),
                        config.getArmorStandPartButtonTemplate(ArmorStandPart.LEFT_ARM)));
        builder.setSlot(
                Menu.index(4, 7),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.BODY),
                        getResetAction(ArmorStandPart.BODY, container),
                        config.getArmorStandPartButtonTemplate(ArmorStandPart.BODY)));
        builder.setSlot(
                Menu.index(4, 8),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.RIGHT_ARM),
                        getResetAction(ArmorStandPart.RIGHT_ARM, container),
                        config.getArmorStandPartButtonTemplate(ArmorStandPart.RIGHT_ARM)));
        builder.setSlot(
                Menu.index(5, 6),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.LEFT_LEG),
                        getResetAction(ArmorStandPart.LEFT_LEG, container),
                        config.getArmorStandPartButtonTemplate(ArmorStandPart.LEFT_LEG)));
        builder.setSlot(
                Menu.index(5, 7),
                new NodeSlot(
                        session,
                        root.getPositionButton(),
                        new YawResetAction(container.get(EntityPropertyTypes.LOCATION), container),
                        config.getArmorStandPositionButtonTemplate()));
        builder.setSlot(
                Menu.index(5, 8),
                new NodeSlot(
                        session,
                        root.getPartButton(ArmorStandPart.RIGHT_LEG),
                        getResetAction(ArmorStandPart.RIGHT_LEG, container),
                        config.getArmorStandPartButtonTemplate(ArmorStandPart.RIGHT_LEG)));
    }

    private Consumer<MenuClick> getResetAction(ArmorStandPart part, PropertyContainer container) {
        Property<EulerAngle> property = container.get(ArmorStandPropertyTypes.POSE.get(part));
        return new ResetAction<>(property, container, EulerAngle.ZERO);
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
