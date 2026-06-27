package me.m56738.easyarmorstands.editor.tool.mode;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import net.kyori.adventure.text.Component;

public class ScaleToolMode implements ToolMode {
    private static final Component NAME = Component.translatable("easyarmorstands.node.scale");

    private ToolContext createContext(ToolProvider provider) {
        return ToolContext.of(provider.position(), provider.rotation());
    }

    @Override
    public Component name() {
        return NAME;
    }

    @Override
    public Node createNode(NodeProvider nodeProvider, ToolProvider toolProvider) {
        return nodeProvider.scale(toolProvider, createContext(toolProvider));
    }
}
