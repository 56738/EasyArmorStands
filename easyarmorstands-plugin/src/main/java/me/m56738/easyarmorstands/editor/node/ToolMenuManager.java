package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.button.AxisMoveToolButton;
import me.m56738.easyarmorstands.editor.button.AxisRotateToolButton;
import me.m56738.easyarmorstands.editor.button.MoveToolButton;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

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

    private void collectButtons(ToolMenuMode mode, List<MenuButton> buttons) {
        PositionProvider positionProvider = toolProvider.position();
        RotationProvider rotationProvider;
        if (mode == ToolMenuMode.LOCAL) {
            rotationProvider = toolProvider.rotation();
        } else {
            rotationProvider = RotationProvider.identity();
        }

        MoveTool moveTool = toolProvider.move(positionProvider, rotationProvider);
        if (moveTool != null) {
            Component name = Message.component("easyarmorstands.node.pick-up");
            buttons.add(new MoveToolButton(session, moveTool, name, ParticleColor.WHITE));
        }
        for (Axis axis : Axis.values()) {
            AxisMoveTool axisMoveTool = toolProvider.move(positionProvider, rotationProvider, axis);
            if (axisMoveTool != null) {
                ParticleColor axisColor = axis.getColor();
                TextComponent axisName = Component.text(axis.getName());
                Component toolName = Message.component("easyarmorstands.node.move-along-axis", axisName)
                        .color(TextColor.color(axisColor));
                buttons.add(new AxisMoveToolButton(session, axisMoveTool, 3, toolName, axisColor));
            }
            AxisRotateTool axisRotateTool = toolProvider.rotate(positionProvider, rotationProvider, axis);
            if (axisRotateTool != null) {
                ParticleColor axisColor = axis.getColor();
                TextComponent axisName = Component.text(axis.getName());
                Component toolName = Message.component("easyarmorstands.node.rotate-around-axis", axisName)
                        .color(TextColor.color(axisColor));
                buttons.add(new AxisRotateToolButton(session, axisRotateTool, 1, 3, toolName, axisColor));
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
