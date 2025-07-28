package me.m56738.easyarmorstands.common.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class DelegateToolProvider implements ToolProvider {
    private final ToolProvider toolProvider;
    private final ToolContext context;

    public DelegateToolProvider(ToolProvider toolProvider, ToolContext context) {
        this.toolProvider = toolProvider;
        this.context = context;
    }

    @Override
    public ToolContext context() {
        return context;
    }

    @Override
    public @Nullable MoveTool move(ToolContext context) {
        return toolProvider.move(context);
    }

    @Override
    public @Nullable AxisMoveTool move(ToolContext context, Axis axis) {
        return toolProvider.move(context, axis);
    }

    @Override
    public @Nullable AxisRotateTool rotate(ToolContext context, Axis axis) {
        return toolProvider.rotate(context, axis);
    }

    @Override
    public @Nullable ScaleTool scale(ToolContext context) {
        return toolProvider.scale(context);
    }

    @Override
    public @Nullable AxisScaleTool scale(ToolContext context, Axis axis) {
        return toolProvider.scale(context, axis);
    }
}
