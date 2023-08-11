package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.property.PropertyContainer;

public interface EditableObject {
    PropertyContainer properties();

    EditableObjectReference asReference();

    Button createButton();

    EditableObjectNode createNode();

    boolean isValid();
}
