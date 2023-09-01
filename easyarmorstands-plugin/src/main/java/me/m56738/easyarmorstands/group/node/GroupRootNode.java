package me.m56738.easyarmorstands.group.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.AddContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.RemoveContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.node.ToolMenuManager;
import me.m56738.easyarmorstands.editor.node.ToolMenuMode;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.GroupMember;
import me.m56738.easyarmorstands.group.tool.GroupToolProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public class GroupRootNode extends MenuNode {
    private final Session session;
    private final Group group;
    private final AxisAlignedBoxParticle boxParticle;
    private final Vector3d minPosition = new Vector3d();
    private final Vector3d maxPosition = new Vector3d();
    private final Vector3d boxCenter = new Vector3d();
    private final Vector3d boxSize = new Vector3d();
    private final ToolMenuManager toolManager;

    public GroupRootNode(Group group) {
        super(group.getSession());
        this.session = group.getSession();
        this.group = group;
        this.boxParticle = session.particleProvider().createAxisAlignedBox();
        this.boxParticle.setColor(ParticleColor.GRAY);
        this.toolManager = new ToolMenuManager(session, this, new GroupToolProvider(group));
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
        toolManager.setMode(ToolMenuMode.GLOBAL);
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
