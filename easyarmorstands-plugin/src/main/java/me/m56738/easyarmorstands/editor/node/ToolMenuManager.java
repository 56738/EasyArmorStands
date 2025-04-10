package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.tool.ToolContextImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToolMenuManager {
    private final Session session;
    private final MenuNode menuNode;
    private final ToolProvider toolProvider;
    private final List<MenuButton> buttons = new ArrayList<>();
    private ToolMenuMode mode = null;

    public ToolMenuManager(Session session, MenuNode menuNode, ToolProvider toolProvider) {
        this.session = session;
        this.menuNode = menuNode;
        this.toolProvider = toolProvider;
        for (ToolMenuMode mode : ToolMenuMode.values()) {
            if (setMode(mode)) {
                return;
            }
        }
        setMode(ToolMenuMode.LOCAL);
    }

    public boolean hasMode(ToolMenuMode mode) {
        List<MenuButton> buttons = new ArrayList<>();
        collectButtons(mode, buttons);
        return !buttons.isEmpty();
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
        return new ToolContextImpl(positionProvider, rotationProvider);
    }

    private void collectButtons(ToolMenuMode mode, List<MenuButton> buttons) {
        ToolContext context = createToolContext();

        if (mode == ToolMenuMode.SCALE) {
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

    public boolean setMode(ToolMenuMode mode) {
        this.mode = Objects.requireNonNull(mode);

        for (MenuButton button : buttons) {
            menuNode.removeButton(button);
        }
        buttons.clear();

        collectButtons(mode, buttons);

        for (MenuButton button : buttons) {
            menuNode.addButton(button);
        }

        return !buttons.isEmpty();
    }
}
