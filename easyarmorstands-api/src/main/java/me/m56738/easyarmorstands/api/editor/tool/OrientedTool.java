package me.m56738.easyarmorstands.api.editor.tool;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;

public interface OrientedTool<S extends ToolSession> extends Tool<S> {
    @Contract(pure = true)
    @NotNull Quaterniondc getRotation();
}
