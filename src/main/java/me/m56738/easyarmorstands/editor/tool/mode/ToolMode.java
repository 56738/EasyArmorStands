package me.m56738.easyarmorstands.editor.tool.mode;

import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import net.kyori.adventure.text.Component;

public interface ToolMode {
    ToolMode LOCAL = new LocalToolMode();
    ToolMode GLOBAL = new GlobalToolMode();
    ToolMode SCALE = new ScaleToolMode();

    Component name();

    Node createNode(NodeProvider nodeProvider, ToolProvider toolProvider);
}
