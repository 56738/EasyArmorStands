package me.m56738.easyarmorstands.api.editor.util;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.node.AbstractNode;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToolManager {
    private final Session session;
    private final AbstractNode node;
    private final ToolProvider toolProvider;
    private final List<MenuButton> buttons = new ArrayList<>();
    private ToolMode mode = null;

    public ToolManager(Session session, AbstractNode node, ToolProvider toolProvider) {
        this.session = session;
        this.node = node;
        this.toolProvider = toolProvider;
        for (ToolMode mode : ToolMode.values()) {
            if (setMode(mode)) {
                return;
            }
        }
        setMode(ToolMode.LOCAL);
    }

    public Session getSession() {
        return session;
    }

    public boolean hasMode(ToolMode mode) {
        List<MenuButton> buttons = new ArrayList<>();
        collectButtons(mode, buttons);
        return !buttons.isEmpty();
    }

    public ToolMode getMode() {
        return mode;
    }

    public ToolMode getNextMode() {
        ToolMode[] modes = ToolMode.values();
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
        if (mode == ToolMode.GLOBAL) {
            return toolProvider.context().withRotation(RotationProvider.identity());
        } else {
            return toolProvider.context();
        }
    }

    private void collectButtons(ToolMode mode, List<MenuButton> buttons) {
        ToolContext context = createToolContext();

        if (mode == ToolMode.SCALE) {
            ScaleTool scaleTool = toolProvider.scale(context);
            if (scaleTool != null && scaleTool.canUse(session.player())) {
                buttons.add(session.menuEntryProvider()
                        .scale()
                        .setTool(scaleTool)
                        .setPriority(1)
                        .build());
            }
            for (Axis axis : Axis.values()) {
                AxisScaleTool axisScaleTool = toolProvider.scale(context, axis);
                if (axisScaleTool != null && axisScaleTool.canUse(session.player())) {
                    buttons.add(session.menuEntryProvider()
                            .axisScale()
                            .setTool(axisScaleTool)
                            .build());
                }
            }
            return;
        }

        MoveTool moveTool = toolProvider.move(context);
        if (moveTool != null && moveTool.canUse(session.player())) {
            buttons.add(session.menuEntryProvider()
                    .move()
                    .setTool(moveTool)
                    .setPriority(1)
                    .build());
        }
        for (Axis axis : Axis.values()) {
            AxisMoveTool axisMoveTool = toolProvider.move(context, axis);
            if (axisMoveTool != null && axisMoveTool.canUse(session.player())) {
                buttons.add(session.menuEntryProvider()
                        .axisMove()
                        .setTool(axisMoveTool)
                        .build());
            }
            AxisRotateTool axisRotateTool = toolProvider.rotate(context, axis);
            if (axisRotateTool != null && axisRotateTool.canUse(session.player())) {
                buttons.add(session.menuEntryProvider()
                        .axisRotate()
                        .setTool(axisRotateTool)
                        .build());
            }
        }
    }

    public boolean setMode(ToolMode mode) {
        this.mode = Objects.requireNonNull(mode);

        for (MenuButton button : buttons) {
            node.removeButton(button);
        }
        buttons.clear();

        collectButtons(mode, buttons);

        for (MenuButton button : buttons) {
            node.addButton(button);
        }

        return !buttons.isEmpty();
    }
}
