package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.menu.MenuButton;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface NodeMenuButton extends MenuButton, Button, NodeFactory {
    @Override
    default Button getButton() {
        return this;
    }

    @Override
    default void onClick(Session session, @Nullable Vector3dc cursor) {
        Node node = createNode();
        if (node != null) {
            session.pushNode(node, cursor);
        }
    }
}
