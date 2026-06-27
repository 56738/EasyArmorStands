package me.m56738.easyarmorstands.api.editor.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CompositeNodeImpl implements Node {
    private final List<Node> nodes;

    CompositeNodeImpl(Collection<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    @Override
    public void onShow(NodeShowContext context) {
        for (Node node : nodes) {
            node.onShow(context);
        }
    }

    @Override
    public void onHide(NodeHideContext context) {
        for (Node node : nodes) {
            node.onHide(context);
        }
    }

    @Override
    public void onUpdate(NodeUpdateContext context) {
        for (Node node : nodes) {
            node.onUpdate(context);
        }
    }
}
