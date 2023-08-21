package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.bone.PositionAndYawBone;
import me.m56738.easyarmorstands.api.editor.bone.PositionBone;
import me.m56738.easyarmorstands.api.editor.bone.RotationBone;
import me.m56738.easyarmorstands.api.editor.bone.RotationProvider;
import me.m56738.easyarmorstands.api.editor.bone.ScaleBone;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A node which can contain multiple {@link Button buttons}.
 */
public abstract class MenuNode implements Node {
    private final Session session;
    private final Component name;
    private final Map<MenuButton, Button> buttons = new HashMap<>();
    private boolean root;
    private MenuButton targetButton;
    private Vector3dc targetCursor;
    private Node nextNode;
    private boolean visible;

    public MenuNode(Session session, Component name) {
        this.session = session;
        this.name = name;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void addButton(MenuButton menuButton) {
        setButton(menuButton, menuButton.createButton());
    }

    public void removeButton(MenuButton menuButton) {
        setButton(menuButton, null);
    }

    private void setButton(MenuButton menuButton, Button button) {
        Button oldButton;
        if (button != null) {
            oldButton = buttons.put(menuButton, button);
        } else {
            oldButton = buttons.remove(menuButton);
        }
        if (visible) {
            if (oldButton != null) {
                oldButton.hidePreview();
            }
            if (button != null) {
                button.update();
                button.updatePreview(false);
                button.showPreview();
            }
        }
    }

    public void addMoveButtons(Session session, PositionBone bone, RotationProvider rotationProvider, double length) {
        for (Axis axis : Axis.values()) {
            addButton(new MoveNode(
                    session,
                    bone,
                    rotationProvider,
                    Message.component("easyarmorstands.node.move-along-axis", Component.text(axis.getName()))
                            .color(TextColor.color(axis.getColor())),
                    axis,
                    axis.getColor(),
                    length
            ));
        }
    }

    public void addPositionButtons(Session session, PositionBone bone, double length) {
        for (Axis axis : Axis.values()) {
            addButton(new MoveNode(
                    session,
                    bone,
                    null,
                    Component.text(axis.getName(), TextColor.color(axis.getColor())),
                    axis,
                    axis.getColor(),
                    length
            ));
        }
    }

    public void addCarryButton(Session session, PositionBone bone) {
        addCarryButton(session, bone, new CarryNode(session, bone));
    }

    public void addCarryButtonWithYaw(Session session, PositionAndYawBone bone) {
        addCarryButton(session, bone, new CarryWithYawNode(session, bone));
    }

    private void addCarryButton(Session session, PositionBone bone, CarryNode node) {
        PositionBoneButton button = new PositionBoneButton(session, bone, node, Message.component("easyarmorstands.node.pick-up"), ParticleColor.YELLOW);
        button.setPriority(1);
        addButton(button);
    }

    public void addYawButton(Session session, PositionAndYawBone bone, double radius) {
        addButton(new YawBoneNode(session, Message.component("easyarmorstands.node.yaw").color(NamedTextColor.YELLOW), ParticleColor.YELLOW, radius, bone));
    }

    public void addRotationButtons(Session session, RotationBone bone, double radius, RotationProvider rotationProvider) {
        for (Axis axis : Axis.values()) {
            addButton(new BoneRotationNode(
                    session,
                    bone,
                    Message.component("easyarmorstands.node.rotate-around-axis", Component.text(axis.getName()))
                            .color(TextColor.color(axis.getColor())),
                    axis,
                    axis.getColor(),
                    radius,
                    rotationProvider
            ));
        }
    }

    public void addScaleButtons(Session session, ScaleBone bone, double length) {
        for (Axis axis : Axis.values()) {
            addButton(new ScaleNode(
                    session,
                    bone,
                    Message.component("easyarmorstands.node.scale-along-axis", Component.text(axis.getName()))
                            .color(TextColor.color(axis.getColor())),
                    axis,
                    ParticleColor.AQUA,
                    length
            ));
        }
    }

    @Override
    public void onEnter(EnterContext context) {
        targetButton = null;
        visible = true;
        for (Button button : buttons.values()) {
            button.update();
            button.updatePreview(false);
            button.showPreview();
        }
    }

    @Override
    public void onExit(ExitContext context) {
        targetButton = null;
        visible = false;
        for (Button button : buttons.values()) {
            button.hidePreview();
        }
    }

    @Override
    public void onUpdate(UpdateContext context) {
        EyeRay ray = context.eyeRay();
        Button bestButton = null;
        MenuButton bestMenuButton = null;
        Vector3dc bestCursor = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        List<ButtonResult> results = new ArrayList<>();
        for (Map.Entry<MenuButton, Button> entry : buttons.entrySet()) {
            MenuButton menuButton = entry.getKey();
            Button button = entry.getValue();
            button.update();
            button.intersect(ray, results::add);
            for (ButtonResult result : results) {
                Vector3dc position = result.position();
                int priority = result.priority();
                if (priority < bestPriority) {
                    continue;
                }
                double distance = position.distanceSquared(ray.origin());
                if (priority > bestPriority || distance < bestDistance) {
                    bestButton = button;
                    bestMenuButton = menuButton;
                    bestCursor = position;
                    bestPriority = priority;
                    bestDistance = distance;
                }
            }
            results.clear();
        }
        for (Button button : buttons.values()) {
            button.updatePreview(button == bestButton);
        }
        Component targetName;
        if (bestButton != null) {
            targetName = bestMenuButton.getName();
        } else {
            targetName = Component.empty();
        }
        session.setActionBar(name);
        session.setTitle(Component.empty());
        session.setSubtitle(targetName);
        targetButton = bestMenuButton;
        targetCursor = bestCursor;
    }

    @Override
    public boolean onClick(ClickContext context) {
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            if (targetButton != null) {
                targetButton.onClick(session, targetCursor);
                return true;
            }
            if (nextNode != null) {
                session.replaceNode(nextNode);
                return true;
            }
        } else if (context.type() == ClickContext.Type.LEFT_CLICK) {
            if (!root) {
                session.popNode();
                return true;
            }
        }
        return false;
    }
}
