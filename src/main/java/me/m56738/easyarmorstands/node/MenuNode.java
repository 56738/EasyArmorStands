package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.PositionAndYawBone;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.bone.RotationBone;
import me.m56738.easyarmorstands.bone.RotationProvider;
import me.m56738.easyarmorstands.bone.ScaleBone;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Axis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.HashMap;
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
                    Component.text("Move " + axis.getName(), TextColor.color(axis.getColor())),
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
        PositionBoneButton button = new PositionBoneButton(session, bone, node, Component.text("Pick up"), ParticleColor.YELLOW);
        button.setPriority(1);
        addButton(button);
    }

    public void addYawButton(Session session, PositionAndYawBone bone, double radius) {
        addButton(new YawBoneNode(session, Component.text("Yaw", NamedTextColor.YELLOW), ParticleColor.YELLOW, radius, bone));
    }

    public void addRotationButtons(Session session, RotationBone bone, double radius, RotationProvider rotationProvider) {
        for (Axis axis : Axis.values()) {
            addButton(new BoneRotationNode(
                    session,
                    bone,
                    Component.text("Rotate " + axis.getName(), TextColor.color(axis.getColor())),
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
                    Component.text("Scale " + axis.getName(), TextColor.color(axis.getColor())),
                    axis,
                    ParticleColor.AQUA,
                    length
            ));
        }
    }

    @Override
    public void onEnter() {
        targetButton = null;
        visible = true;
        for (Button button : buttons.values()) {
            button.update();
            button.updatePreview(false);
            button.showPreview();
        }
    }

    @Override
    public void onExit() {
        targetButton = null;
        visible = false;
        for (Button button : buttons.values()) {
            button.hidePreview();
        }
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        Button bestButton = null;
        MenuButton bestMenuButton = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (Map.Entry<MenuButton, Button> entry : buttons.entrySet()) {
            MenuButton menuButton = entry.getKey();
            Button button = entry.getValue();
            button.update();
            button.updateLookTarget(eyes, target);
            Vector3dc position = button.getLookTarget();
            if (position == null) {
                continue;
            }
            int priority = button.getLookPriority();
            if (priority < bestPriority) {
                continue;
            }
            double distance = position.distanceSquared(eyes);
            if (priority > bestPriority || distance < bestDistance) {
                bestButton = button;
                bestMenuButton = menuButton;
                bestPriority = priority;
                bestDistance = distance;
            }
        }
        for (Button button : buttons.values()) {
            button.updatePreview(button == bestButton);
        }
        Component targetName;
        if (bestButton != null) {
            targetName = bestButton.getName();
        } else {
            targetName = Component.empty();
        }
        session.sendActionBar(name);
        session.showTitle(Title.title(Component.empty(), targetName,
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ZERO)));
        targetButton = bestMenuButton;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.RIGHT_CLICK) {
            if (targetButton != null) {
                targetButton.onClick(session);
                return true;
            }
            if (nextNode != null) {
                session.replaceNode(nextNode);
                return true;
            }
        } else if (context.getType() == ClickType.LEFT_CLICK) {
            if (!root) {
                session.popNode();
                return true;
            }
        }
        return false;
    }
}
