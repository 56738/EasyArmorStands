package me.m56738.easyarmorstands.node;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

public interface ClickableNode extends Node {
    Component getName();

    default int getPriority() {
        return 0;
    }

    Vector3dc updatePreview(Vector3dc eyes, Vector3dc target);

    void showPreview(boolean focused);
}
