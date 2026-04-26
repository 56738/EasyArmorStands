package me.m56738.easyarmorstands.api.editor.layer;

import me.m56738.easyarmorstands.api.editor.node.Node;

public interface NodeLayer extends Layer {
    void addNode(Node node);

    void removeNode(Node node);
}
