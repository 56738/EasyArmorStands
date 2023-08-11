package me.m56738.easyarmorstands.node;

import org.joml.Vector3dc;

public interface Node {
    void onEnter();

    void onExit();

    default void onAdd() {
    }

    default void onRemove() {
    }

    void onUpdate(Vector3dc eyes, Vector3dc target);

    default void onInactiveUpdate() {
    }

    boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context);

    boolean isValid();
}
