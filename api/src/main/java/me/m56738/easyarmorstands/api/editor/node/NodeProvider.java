package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisMoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisRotateToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisScaleToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.MoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ScaleToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.NonExtendable
public interface NodeProvider {
    @Contract(pure = true)
    MoveToolNodeBuilder move();

    @Contract(pure = true)
    AxisMoveToolNodeBuilder axisMove();

    @Contract(pure = true)
    ScaleToolNodeBuilder scale();

    @Contract(pure = true)
    AxisScaleToolNodeBuilder axisScale();

    @Contract(pure = true)
    AxisRotateToolNodeBuilder axisRotate();

    @Contract(pure = true)
    Node tools(ToolProvider toolProvider);

    @Contract(pure = true)
    default Node move(ToolProvider toolProvider, ToolContext toolContext) {
        List<Node> nodes = new ArrayList<>();
        for (Axis axis : Axis.values()) {
            AxisMoveTool axisMoveTool = toolProvider.move(toolContext, axis);
            if (axisMoveTool != null) {
                nodes.add(axisMove()
                        .setTool(axisMoveTool)
                        .build());
            }
        }
        MoveTool moveTool = toolProvider.move(toolContext);
        if (moveTool != null) {
            nodes.add(move()
                    .setTool(moveTool)
                    .setPriority(1)
                    .build());
        }
        return Node.composite(nodes);
    }

    @Contract(pure = true)
    default Node rotate(ToolProvider toolProvider, ToolContext toolContext) {
        List<Node> nodes = new ArrayList<>();
        for (Axis axis : Axis.values()) {
            AxisRotateTool axisRotateTool = toolProvider.rotate(toolContext, axis);
            if (axisRotateTool != null) {
                nodes.add(axisRotate()
                        .setTool(axisRotateTool)
                        .build());
            }
        }
        return Node.composite(nodes);
    }

    @Contract(pure = true)
    default Node scale(ToolProvider toolProvider, ToolContext toolContext) {
        List<Node> nodes = new ArrayList<>();
        for (Axis axis : Axis.values()) {
            AxisScaleTool axisScaleTool = toolProvider.scale(toolContext, axis);
            if (axisScaleTool != null) {
                nodes.add(axisScale()
                        .setTool(axisScaleTool)
                        .build());
            }
        }
        ScaleTool scaleTool = toolProvider.scale(toolContext);
        if (scaleTool != null) {
            nodes.add(scale()
                    .setTool(scaleTool)
                    .setPriority(1)
                    .build());
        }
        return Node.composite(nodes);
    }
}
