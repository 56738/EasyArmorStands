package me.m56738.easyarmorstands.editor.util;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.AbstractLayer;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToolMenuManager {
    private final Session session;
    private final AbstractLayer layer;
    private final ToolProvider toolProvider;
    private final List<Node> nodes = new ArrayList<>();
    private ToolMenuMode mode = null;

    public ToolMenuManager(Session session, AbstractLayer layer, ToolProvider toolProvider) {
        this.session = session;
        this.layer = layer;
        this.toolProvider = toolProvider;
        for (ToolMenuMode mode : ToolMenuMode.values()) {
            if (setMode(mode)) {
                return;
            }
        }
        setMode(ToolMenuMode.LOCAL);
    }

    public Session getSession() {
        return session;
    }

    public boolean hasMode(ToolMenuMode mode) {
        List<Node> nodes = new ArrayList<>();
        collectNodes(mode, nodes);
        return !nodes.isEmpty();
    }

    public ToolMenuMode getMode() {
        return mode;
    }

    public ToolMenuMode getNextMode() {
        ToolMenuMode[] modes = ToolMenuMode.values();
        for (int i = mode.ordinal() + 1; i < modes.length; i++) {
            if (hasMode(modes[i])) {
                return modes[i];
            }
        }
        for (int i = 0; i < mode.ordinal(); i++) {
            if (hasMode(modes[i])) {
                return modes[i];
            }
        }
        return mode;
    }

    private ToolContext createToolContext() {
        PositionProvider positionProvider = toolProvider.position();
        RotationProvider rotationProvider;
        if (mode == ToolMenuMode.GLOBAL) {
            rotationProvider = RotationProvider.identity();
        } else {
            rotationProvider = toolProvider.rotation();
        }
        return ToolContext.of(positionProvider, rotationProvider);
    }

    private void collectNodes(ToolMenuMode mode, List<Node> nodes) {
        ToolContext context = createToolContext();

        if (mode == ToolMenuMode.SCALE) {
            ScaleTool scaleTool = toolProvider.scale(context);
            if (scaleTool != null && scaleTool.canUse(session.player())) {
                nodes.add(session.nodeProvider()
                        .scale()
                        .setTool(scaleTool)
                        .setPriority(1)
                        .build());
            }
            for (Axis axis : Axis.values()) {
                AxisScaleTool axisScaleTool = toolProvider.scale(context, axis);
                if (axisScaleTool != null && axisScaleTool.canUse(session.player())) {
                    nodes.add(session.nodeProvider()
                            .axisScale()
                            .setTool(axisScaleTool)
                            .build());
                }
            }
            return;
        }

        MoveTool moveTool = toolProvider.move(context);
        if (moveTool != null && moveTool.canUse(session.player())) {
            nodes.add(session.nodeProvider()
                    .move()
                    .setTool(moveTool)
                    .setPriority(1)
                    .build());
        }
        for (Axis axis : Axis.values()) {
            AxisMoveTool axisMoveTool = toolProvider.move(context, axis);
            if (axisMoveTool != null && axisMoveTool.canUse(session.player())) {
                nodes.add(session.nodeProvider()
                        .axisMove()
                        .setTool(axisMoveTool)
                        .build());
            }
            AxisRotateTool axisRotateTool = toolProvider.rotate(context, axis);
            if (axisRotateTool != null && axisRotateTool.canUse(session.player())) {
                nodes.add(session.nodeProvider()
                        .axisRotate()
                        .setTool(axisRotateTool)
                        .build());
            }
        }
    }

    public boolean setMode(ToolMenuMode mode) {
        this.mode = Objects.requireNonNull(mode);

        for (Node node : nodes) {
            layer.removeNode(node);
        }
        nodes.clear();

        collectNodes(mode, nodes);

        for (Node node : nodes) {
            layer.addNode(node);
        }

        return !nodes.isEmpty();
    }
}
