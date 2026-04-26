package me.m56738.easyarmorstands.api.editor.node;

public interface Node {
    default void onShow(NodeShowContext context) {
    }

    default void onHide(NodeHideContext context) {
    }

    default void onUpdate(NodeUpdateContext context) {
    }
}
