package me.m56738.easyarmorstands.editor.tool.mode;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import net.kyori.adventure.text.Component;

public class LocalToolMode extends AbstractDefaultToolMode {
    private static final Component NAME = Component.translatable("easyarmorstands.node.local");

    @Override
    public Component name() {
        return NAME;
    }

    @Override
    public ToolContext createContext(ToolProvider provider) {
        return ToolContext.of(provider.position(), provider.rotation());
    }

    @Override
    public Node createNode(NodeProvider nodeProvider, ToolProvider toolProvider) {
        if (toolProvider.rotation() == RotationProvider.identity()) {
            return Node.empty();
        }
        return super.createNode(nodeProvider, toolProvider);
    }
}
