package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.MatrixBone;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Axis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * A node which can contain multiple {@link Button buttons}.
 * Clicking a button {@link Button#createNode() creates a node} and navigates to it.
 */
public class MenuNode implements Node {
    private final Session session;
    private final Component name;
    private final List<Button> buttons = new ArrayList<>();
    private boolean root;
    private Button targetButton;
    private Node nextNode;

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

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void removeButton(Button button) {
        buttons.remove(button);
    }

    public void addMoveButtons(Session session, PositionBone bone, double length, boolean includeEnds) {
        for (Axis axis : Axis.values()) {
            addButton(new MoveNode(
                    session,
                    bone,
                    Component.text("Move " + axis.getName(), axis.getColor()),
                    axis.getDirection(),
                    axis.getColor(),
                    length,
                    true,
                    includeEnds
            ));
        }
    }

    public void addPositionButtons(Session session, PositionBone bone, double length, boolean includeEnds) {
        for (Axis axis : Axis.values()) {
            addButton(new MoveNode(
                    session,
                    bone,
                    Component.text(axis.getName(), axis.getColor()),
                    axis.getDirection(),
                    axis.getColor(),
                    length,
                    false,
                    includeEnds
            ));
        }
    }

    public void addRotationButtons(Session session, MatrixBone bone, double radius, boolean local) {
        for (Axis axis : Axis.values()) {
            addButton(new BoneRotationNode(
                    session,
                    bone,
                    Component.text("Rotate " + axis.getName(), axis.getColor()),
                    axis.getDirection(),
                    axis.getColor(),
                    radius,
                    local
            ));
        }
    }

    public void addScaleNodes(Session session, MatrixBone bone, double length) {
        for (Axis axis : Axis.values()) {
            addButton(new ScaleNode(
                    session,
                    bone,
                    Component.text("Scale " + axis.getName(), axis.getColor()),
                    axis.getDirection(),
                    new Vector3d(1, 1, 1).sub(axis.getDirection()),
                    NamedTextColor.AQUA,
                    length
            ));
        }
    }

    @Override
    public void onEnter() {
        targetButton = null;
    }

    @Override
    public void onExit() {
        targetButton = null;
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        Button bestButton = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (Button button : buttons) {
            button.update(eyes, target);
            Vector3dc position = button.getLookTarget();
            if (position == null) {
                continue;
            }
            int priority = button.getPriority();
            if (priority < bestPriority) {
                continue;
            }
            double distance = position.distanceSquared(eyes);
            if (priority > bestPriority || distance < bestDistance) {
                bestButton = button;
                bestPriority = priority;
                bestDistance = distance;
            }
        }
        for (Button button : buttons) {
            button.showPreview(button == bestButton);
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
        targetButton = bestButton;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.RIGHT_CLICK) {
            if (targetButton != null) {
                Node node = targetButton.createNode();
                if (node != null) {
                    session.pushNode(node);
                }
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
