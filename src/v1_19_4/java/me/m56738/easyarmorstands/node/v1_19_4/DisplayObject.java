package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;

public class DisplayObject<T extends Display> extends SimpleEntityObject {
    private final Session session;
    private final T entity;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayObject(Session session, T entity, DisplayRootNodeFactory<T> factory) {
        super(session, entity);
        this.session = session;
        this.entity = entity;
        this.factory = factory;
    }

    @Override
    public boolean hasItemSlots() {
        return entity instanceof ItemDisplay;
    }

    @Override
    public Button createButton() {
        return new DisplayButton<>(session, entity);
    }

    @Override
    public EditableObjectNode createNode() {
        PropertyContainer container = session.properties(this);

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
