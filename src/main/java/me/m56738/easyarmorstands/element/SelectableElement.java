package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.session.Session;

public interface SelectableElement extends Element {
    Button createButton(Session session);

    Node createNode(Session session);
}
