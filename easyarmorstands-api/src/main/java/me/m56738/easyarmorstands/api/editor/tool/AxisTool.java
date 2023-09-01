package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface AxisTool<S extends ToolSession> extends PositionedTool<S>, OrientedTool<S> {
    @Contract(pure = true)
    @NotNull Axis getAxis();
}
