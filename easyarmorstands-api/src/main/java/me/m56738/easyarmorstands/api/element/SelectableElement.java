package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;

public interface SelectableElement extends EditableElement, NamedElement {
    Button createButton(Session session);

    Node createNode(Session session);
}
