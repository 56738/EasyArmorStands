package me.m56738.easyarmorstands.group.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
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

    public GroupToolProvider(Group group) {
        this.group = group;
        this.positionProvider = new GroupPositionProvider(group);
    }

    @Override
    public @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public @NotNull RotationProvider rotation() {
        return RotationProvider.identity();
    }

    @Override
    public @Nullable MoveTool move(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider) {
        List<MoveTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            MoveTool tool = member.getTools().move(positionProvider, rotationProvider);
            if (tool == null) {
                return ToolProvider.super.move(positionProvider, rotationProvider);
            }
            tools.add(tool);
        }
        return new GroupMoveTool(positionProvider, rotationProvider, tools);
    }

    @Override
    public @Nullable AxisMoveTool move(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider,
            @NotNull Axis axis) {
        List<AxisMoveTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            AxisMoveTool tool = member.getTools().move(positionProvider, rotationProvider, axis);
            if (tool == null) {
                return ToolProvider.super.move(positionProvider, rotationProvider, axis);
            }
            tools.add(tool);
        }
        return new GroupAxisMoveTool(positionProvider, rotationProvider, axis, tools);
    }

    @Override
    public @Nullable AxisRotateTool rotate(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider,
            @NotNull Axis axis) {
        List<AxisRotateTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            AxisRotateTool tool = member.getTools().rotate(positionProvider, rotationProvider, axis);
            if (tool == null) {
                return ToolProvider.super.rotate(positionProvider, rotationProvider, axis);
            }
            tools.add(tool);
        }
        return new GroupAxisRotateTool(positionProvider, rotationProvider, axis, tools);
    }
}
