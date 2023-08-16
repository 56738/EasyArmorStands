package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.PositionAndYawBone;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.session.Session;

public class CarryWithYawNode extends CarryNode {
    private final Session session;
    private final PositionAndYawBone bone;
    private float initialYaw;
    private float currentYaw;
    private float yawOffset;

    public CarryWithYawNode(Session session, PositionAndYawBone bone) {
        super(session, bone);
        this.session = session;
        this.bone = bone;
    }

    @Override
    public void onEnter() {
        super.onEnter();
        initialYaw = bone.getYaw();
        yawOffset = initialYaw - session.getPlayer().yaw();
    }

    @Override
    protected void abort() {
        bone.setPositionAndYaw(initialPosition, initialYaw);
    }

    @Override
    protected void update() {
        super.update();
        EasPlayer player = session.getPlayer();
        float pitch = player.pitch();
        if (pitch > 80) {
            currentYaw = player.yaw();
        } else {
            currentYaw = (float) session.snapAngle(player.yaw() + yawOffset - initialYaw) + initialYaw;
        }
    }

    @Override
    protected void apply() {
        bone.setPositionAndYaw(currentPosition, currentYaw);
    }
}
