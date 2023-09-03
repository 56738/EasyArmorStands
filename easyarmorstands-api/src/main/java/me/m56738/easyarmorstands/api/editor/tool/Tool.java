package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.NotNull;

public interface Tool<S extends ToolSession> {
    @NotNull S start();
}
