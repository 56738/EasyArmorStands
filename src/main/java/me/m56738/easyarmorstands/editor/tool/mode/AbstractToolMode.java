package me.m56738.easyarmorstands.editor.tool.mode;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;

import java.util.List;

public abstract class AbstractToolMode implements ToolMode {
    @Override
    public Node createNode(NodeProvider nodeProvider, ToolProvider toolProvider) {
        return Node.composite(createNodes(nodeProvider, toolProvider));
    }

    protected abstract List<Node> createNodes(NodeProvider nodeProvider, ToolProvider toolProvider);
}
