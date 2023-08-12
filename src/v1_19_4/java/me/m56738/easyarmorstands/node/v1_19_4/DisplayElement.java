package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;

public class DisplayElement<T extends Display> extends SimpleEntityElement<T> {
    private final T entity;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayElement(T entity, SimpleEntityElementType<T> type, DisplayRootNodeFactory<T> factory) {
        super(entity, type);
        this.entity = entity;
        this.factory = factory;
    }

    @Override
    public boolean hasItemSlots() {
        return entity instanceof ItemDisplay;
    }

    @Override
    public Button createButton(Session session) {
        return new DisplayButton<>(session, entity);
    }

    @Override
    public ElementNode createNode(Session session) {
        PropertyContainer container = PropertyContainer.tracked(this, session.getPlayer());

        DisplayBone bone = new DisplayBone(container, DisplayLeftRotationProperty.TYPE);

        DisplayRootNode localNode = factory.createRootNode(session, Component.text("Local"), this);
        localNode.setRoot(true);
        localNode.addMoveButtons(session, bone, bone, 2);
        localNode.addCarryButtonWithYaw(session, bone);
        localNode.addRotationButtons(session, bone, 1, bone);
        localNode.addScaleButtons(session, bone, 2);

        DisplayRootNode globalNode = factory.createRootNode(session, Component.text("Global"), this);
        globalNode.setRoot(true);
        globalNode.addPositionButtons(session, bone, 3);
        globalNode.addCarryButtonWithYaw(session, bone);
        globalNode.addRotationButtons(session, bone, 1, null);
        globalNode.addYawButton(session, bone, 1.5);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        return localNode;
    }
}
