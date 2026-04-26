package me.m56738.easyarmorstands.api.editor.node.tool;

import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.node.Node;

public interface ToolNode extends Node {
    Layer createLayer();
}
