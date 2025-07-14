package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.GroupMember;
import me.m56738.easyarmorstands.group.GroupPositionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GroupToolProvider implements ToolProvider {
    private final Group group;
    private final PositionProvider positionProvider;
    private final ChangeContext changeContext;

    public GroupToolProvider(Group group, ChangeContext changeContext) {
        this.group = group;
        this.positionProvider = new GroupPositionProvider(group);
        this.changeContext = changeContext;
    }

    @Override
    public @NotNull ToolContext context() {
        return ToolContext.of(positionProvider, RotationProvider.identity());
    }

    @Override
    public @Nullable MoveTool move(@NotNull ToolContext context) {
        List<MoveTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            MoveTool tool = member.getElement().getTools(changeContext).move(context);
            if (tool == null) {
                return ToolProvider.super.move(context);
            }
            tools.add(tool);
        }
        return new GroupMoveTool(context, changeContext, tools);
    }

    @Override
    public @Nullable AxisMoveTool move(@NotNull ToolContext context, @NotNull Axis axis) {
        List<AxisMoveTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            AxisMoveTool tool = member.getElement().getTools(changeContext).move(context, axis);
            if (tool == null) {
                return ToolProvider.super.move(context, axis);
            }
            tools.add(tool);
        }
        return new GroupAxisMoveTool(context, changeContext, axis, tools);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        List<AxisRotateTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            AxisRotateTool tool = member.getElement().getTools(changeContext).rotate(context, axis);
            if (tool == null) {
                return ToolProvider.super.rotate(context, axis);
            }
            tools.add(tool);
        }
        return new GroupAxisRotateTool(context, changeContext, axis, tools);
    }
}
