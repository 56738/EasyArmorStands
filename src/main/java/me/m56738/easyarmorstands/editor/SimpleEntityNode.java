package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;

public class SimpleEntityNode extends MenuNode implements EditableObjectNode {
    private final EditableObject editableObject;

    public SimpleEntityNode(Session session, Component name, EditableObject editableObject) {
        super(session, name);
        this.editableObject = editableObject;

        EntityLocationBone bone = new EntityLocationBone(session.properties(editableObject));
        setRoot(true);
        addPositionButtons(session, bone, 3);
        addCarryButtonWithYaw(session, bone);
    }

    @Override
    public boolean isValid() {
        return editableObject.isValid();
    }

    @Override
    public EditableObject getEditableObject() {
        return editableObject;
    }
}
