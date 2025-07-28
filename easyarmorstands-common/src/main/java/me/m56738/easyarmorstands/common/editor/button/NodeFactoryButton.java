package me.m56738.easyarmorstands.common.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.common.editor.node.NodeFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface NodeFactoryButton extends MenuButton, Button, NodeFactory {
    @Override
    default @NotNull Button getButton() {
        return this;
    }

    @Override
    default void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
        Node node = createNode();
        if (node != null) {
            session.pushNode(node, cursor);
        }
    }
}
