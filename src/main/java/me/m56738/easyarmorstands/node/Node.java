package me.m56738.easyarmorstands.node;

import org.joml.Vector3dc;

public interface Node {
    void onEnter();

    void onExit();

    void onUpdate(Vector3dc eyes, Vector3dc target);

    boolean onClick(Vector3dc eyes, Vector3dc target, ClickType type);
}
