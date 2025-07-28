package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.editor.context.AddContextImpl;
import me.m56738.easyarmorstands.common.editor.context.EnterContextImpl;
import me.m56738.easyarmorstands.common.editor.context.ExitContextImpl;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class NodeStack {
    private final SessionImpl session;
    private final List<Node> nodes = new ArrayList<>();

    public NodeStack(SessionImpl session) {
        this.session = session;
    }

    public Node getNode() {
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    public void pushNode(Node node, @Nullable Vector3dc cursor) {
        Node current = getNode();
        if (current != null) {
            current.onExit(ExitContextImpl.INSTANCE);
        }
        nodes.add(node);
        node.onAdd(AddContextImpl.INSTANCE);
        node.onEnter(new EnterContextImpl(session, cursor));
    }
}
