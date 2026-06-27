package me.m56738.easyarmorstands.editor.tool.mode;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;

import java.util.List;

public abstract class AbstractDefaultToolMode extends AbstractToolMode {
    protected abstract ToolContext createContext(ToolProvider provider);

    @Override
    protected List<Node> createNodes(NodeProvider nodeProvider, ToolProvider toolProvider) {
        ToolContext context = createContext(toolProvider);
        return List.of(
                nodeProvider.move(toolProvider, context),
                nodeProvider.rotate(toolProvider, context));
    }
}
