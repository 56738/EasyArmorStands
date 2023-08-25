package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.bone.DisplayBone;
import me.m56738.easyarmorstands.display.editor.button.DisplayButton;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNode;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNodeFactory;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.node.ElementNode;
import org.bukkit.entity.Display;

public class DisplayElement<T extends Display> extends SimpleEntityElement<T> {
    private final T entity;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayElement(T entity, SimpleEntityElementType<T> type, DisplayRootNodeFactory<T> factory) {
        super(entity, type);
        this.entity = entity;
        this.factory = factory;
    }

    @Override
    public Button createButton(Session session) {
        return new DisplayButton(session, getProperties());
    }

    @Override
    public ElementNode createNode(Session session) {
        PropertyContainer container = session.properties(this);

        DisplayBone bone = new DisplayBone(container, DisplayPropertyTypes.LEFT_ROTATION);

        DisplayRootNode localNode = factory.createRootNode(session, Message.component("easyarmorstands.node.local"), this);
        localNode.setRoot(true);
        localNode.addMoveButtons(session, bone, bone, 2);
        localNode.addCarryButtonWithYaw(session, bone);
        localNode.addRotationButtons(session, bone, 1, bone);
        localNode.addScaleButtons(session, bone, 2);

        DisplayRootNode globalNode = factory.createRootNode(session, Message.component("easyarmorstands.node.global"), this);
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
