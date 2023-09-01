package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.Contract;

public interface ToolSession {
    void revert();

    void commit();

    @Contract(pure = true)
    boolean isValid();
}
