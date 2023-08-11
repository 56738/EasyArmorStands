package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;

public interface NodeButton extends MenuButton, Button, NodeFactory {
    @Override
    default Button createButton() {
        return this;
    }

    @Override
    default void onClick(Session session) {
        Node node = createNode();
        if (node != null) {
            session.pushNode(node);
        }
    }
}
