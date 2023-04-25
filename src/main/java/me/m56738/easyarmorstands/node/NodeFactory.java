package me.m56738.easyarmorstands.node;

public interface NodeFactory {
    /**
     * Creates a node that should be entered when this button is clicked.
     *
     * @return The node which can be entered using this button.
     */
    Node createNode();
}
