package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.MatrixBone;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ParentNode implements Node {
    private final Session session;
    private final Component name;
    private final List<ClickableNode> children = new ArrayList<>();
    private Node targetNode;
    private Node nextNode;

    public ParentNode(Session session, Component name) {
        this.session = session;
        this.name = name;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void addNode(ClickableNode node) {
        children.add(node);
    }

    public void addMoveNodes(Session session, PositionBone bone, double length, boolean includeEnds) {
        addNode(new MoveNode(session, bone, Component.text("Move X", NamedTextColor.RED), Util.X, NamedTextColor.RED, length, true, includeEnds));
        addNode(new MoveNode(session, bone, Component.text("Move Y", NamedTextColor.GREEN), Util.Y, NamedTextColor.GREEN, length, true, includeEnds));
        addNode(new MoveNode(session, bone, Component.text("Move Z", NamedTextColor.BLUE), Util.Z, NamedTextColor.BLUE, length, true, includeEnds));
    }

    public void addPositionNodes(Session session, PositionBone bone, double length, boolean includeEnds) {
        addNode(new MoveNode(session, bone, Component.text("X", NamedTextColor.RED), Util.X, NamedTextColor.RED, length, false, includeEnds));
        addNode(new MoveNode(session, bone, Component.text("Y", NamedTextColor.GREEN), Util.Y, NamedTextColor.GREEN, length, false, includeEnds));
        addNode(new MoveNode(session, bone, Component.text("Z", NamedTextColor.BLUE), Util.Z, NamedTextColor.BLUE, length, false, includeEnds));
    }

    public void addRotationNodes(Session session, MatrixBone bone, double radius, boolean local) {
        addNode(new BoneRotationNode(session, bone, Component.text("Rotate X", NamedTextColor.RED), Util.X, NamedTextColor.RED, radius, local));
        addNode(new BoneRotationNode(session, bone, Component.text("Rotate Y", NamedTextColor.GREEN), Util.Y, NamedTextColor.GREEN, radius, local));
        addNode(new BoneRotationNode(session, bone, Component.text("Rotate Z", NamedTextColor.BLUE), Util.Z, NamedTextColor.BLUE, radius, local));
    }

    public void addScaleNodes(Session session, MatrixBone bone, double length) {
        addNode(new ScaleNode(session, bone, Component.text("Scale X", NamedTextColor.RED), Util.X, Util.YZ, NamedTextColor.AQUA, length));
        addNode(new ScaleNode(session, bone, Component.text("Scale Y", NamedTextColor.GREEN), Util.Y, Util.XZ, NamedTextColor.AQUA, length));
        addNode(new ScaleNode(session, bone, Component.text("Scale Z", NamedTextColor.BLUE), Util.Z, Util.XY, NamedTextColor.AQUA, length));
    }

    @Override
    public void onEnter() {
        targetNode = null;
    }

    @Override
    public void onExit() {
        targetNode = null;
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        ClickableNode bestNode = null;
        int bestPriority = Integer.MIN_VALUE;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (ClickableNode child : children) {
            Vector3dc position = child.updatePreview(eyes, target);
            if (position == null) {
                continue;
            }
            int priority = child.getPriority();
            if (priority < bestPriority) {
                continue;
            }
            double distance = position.distanceSquared(eyes);
            if (priority > bestPriority || distance < bestDistance) {
                bestNode = child;
                bestPriority = priority;
                bestDistance = distance;
            }
        }
        for (ClickableNode child : children) {
            child.showPreview(child == bestNode);
        }
        Component targetName;
        if (bestNode != null) {
            targetName = bestNode.getName();
        } else {
            targetName = Component.empty();
        }
        session.sendActionBar(name);
        session.showTitle(Title.title(Component.empty(), targetName,
                Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ZERO)));
        targetNode = bestNode;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickType type) {
        if (type == ClickType.RIGHT_CLICK) {
            if (targetNode != null) {
                session.pushNode(targetNode);
                return true;
            }
            if (nextNode != null) {
                session.replaceNode(nextNode);
                return true;
            }
        }
        return false;
    }
}
