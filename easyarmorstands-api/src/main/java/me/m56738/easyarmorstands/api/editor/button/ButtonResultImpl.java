package me.m56738.easyarmorstands.api.editor.button;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

class ButtonResultImpl implements ButtonResult {
    private final Vector3dc position;
    private final int priority;

    ButtonResultImpl(Vector3dc position, int priority) {
        this.position = position;
        this.priority = priority;
    }

    @Override
    public @NotNull Vector3dc position() {
        return position;
    }

    @Override
    public int priority() {
        return priority;
    }
}
