package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.editor.node.NodeFactory;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface NodeFactoryButton extends MenuButton, Button, NodeFactory {
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
