package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.node.tool.AxisMoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisRotateToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisScaleToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.MoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ScaleToolNodeBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NodeProvider {
    @Contract(pure = true)
    @NotNull MoveToolNodeBuilder move();

    @Contract(pure = true)
    @NotNull AxisMoveToolNodeBuilder axisMove();

    @Contract(pure = true)
    @NotNull ScaleToolNodeBuilder scale();

    @Contract(pure = true)
    @NotNull AxisScaleToolNodeBuilder axisScale();

    @Contract(pure = true)
    @NotNull AxisRotateToolNodeBuilder axisRotate();
}
