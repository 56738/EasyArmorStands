package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;

public interface EditableObject {
    PropertyContainer properties();

    EditableObjectReference asReference();

    Button createButton(Session session);

    EditableObjectNode createNode(Session session);

    boolean isValid();

    boolean hasItemSlots();
}
