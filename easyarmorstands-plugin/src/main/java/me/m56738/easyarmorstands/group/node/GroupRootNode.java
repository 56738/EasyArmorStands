package me.m56738.easyarmorstands.group.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.group.GroupMember;
import me.m56738.easyarmorstands.api.group.GroupMoveAxisTool;
import me.m56738.easyarmorstands.api.group.GroupMoveTool;
import me.m56738.easyarmorstands.api.group.GroupRotateTool;
import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.axis.GroupCarryAxis;
import me.m56738.easyarmorstands.group.axis.GroupMoveAxis;
import me.m56738.easyarmorstands.group.axis.GroupRotateAxis;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class GroupRootNode extends MenuNode {
    private final Session session;
    private final Group group;
    private final AxisAlignedBoxParticle boxParticle;
    private final Vector3d minPosition = new Vector3d();
    private final Vector3d maxPosition = new Vector3d();
    private final Vector3d boxCenter = new Vector3d();
    private final Vector3d boxSize = new Vector3d();
    private final List<MenuButton> buttons = new ArrayList<>();

    public GroupRootNode(Group group) {
        super(group.getSession());
        this.session = group.getSession();
        this.group = group;
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
        this.boxParticle.setColor(ParticleColor.GRAY);
    }

    private void updateTools() {
        for (MenuButton button : buttons) {
            removeButton(button);
        }
        buttons.clear();
        Vector3dc anchor = group.getAveragePosition();
        addMoveTool();
        for (Axis axis : Axis.values()) {
            addMoveTool(axis);
            addRotateTool(anchor, axis);
        }
    }

    private void addMoveTool() {
        List<GroupMoveTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            GroupMoveTool tool = member.move();
            if (tool != null) {
                tools.add(tool);
            }
        }
        if (tools.isEmpty()) {
            return;
        }
        addTool(session.menuEntryProvider()
                .carry()
                .setAxis(new GroupCarryAxis(group, tools))
                .setPriority(1)
                .build());
    }

    private void addMoveTool(Axis axis) {
        List<GroupMoveAxisTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            GroupMoveAxisTool tool = member.move(axis);
            if (tool != null) {
                tools.add(tool);
            }
        }
        if (tools.isEmpty()) {
            return;
        }
        addTool(session.menuEntryProvider()
                .move()
                .setAxis(new GroupMoveAxis(group, axis, tools))
                .build());
    }

    private void addRotateTool(Vector3dc anchor, Axis axis) {
        List<GroupRotateTool> tools = new ArrayList<>();
        for (GroupMember member : group.getMembers()) {
            GroupRotateTool tool = member.rotate(anchor, axis);
            if (tool != null) {
                tools.add(tool);
            }
        }
        if (tools.isEmpty()) {
            return;
        }
        addTool(session.menuEntryProvider()
                .rotate()
                .setAxis(new GroupRotateAxis(anchor, axis, tools))
                .build());
    }

    private void addTool(MenuButton button) {
        buttons.add(button);
        addButton(button);
    }

    private void updateBox() {
        minPosition.set(Double.POSITIVE_INFINITY);
        maxPosition.set(Double.NEGATIVE_INFINITY);
        boolean valid = false;
        for (GroupMember member : group.getMembers()) {
            BoundingBox boundingBox = member.getBoundingBox();
            minPosition.min(boundingBox.getMinPosition());
            maxPosition.max(boundingBox.getMaxPosition());
            valid = true;
        }
        if (!valid) {
            return;
        }

        maxPosition.add(minPosition, boxCenter).div(2);
        maxPosition.sub(minPosition, boxSize);

        boxParticle.setCenter(boxCenter);
        boxParticle.setSize(boxSize);
    }

    @Override
    public void onAdd(@NotNull AddContext context) {
        super.onAdd(context);
        session.addParticle(boxParticle);
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        super.onEnter(context);
        boxParticle.setColor(ParticleColor.WHITE);
        updateTools();
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        group.update();
        updateBox();
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        boxParticle.setColor(ParticleColor.GRAY);
        super.onExit(context);
    }

    @Override
    public void onInactiveUpdate(@NotNull UpdateContext context) {
        super.onInactiveUpdate(context);
        group.update();
        updateBox();
    }

    @Override
    public void onRemove(@NotNull RemoveContext context) {
        session.removeParticle(boxParticle);
        super.onRemove(context);
    }

    @Override
    public boolean isValid() {
        return group.isValid();
    }

    public Group getGroup() {
        return group;
    }
}
