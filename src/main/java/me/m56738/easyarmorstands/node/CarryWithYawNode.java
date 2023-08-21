package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.bone.PositionAndYawBone;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import org.bukkit.Location;

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
    public void onEnter(EnterContext context) {
        super.onEnter(context);
        initialYaw = bone.getYaw();
        yawOffset = initialYaw - session.player().getLocation().getYaw();
    }

    @Override
    protected void abort() {
        bone.setPositionAndYaw(initialPosition, initialYaw);
    }

    @Override
    protected void update() {
        super.update();
        Location location = session.player().getLocation();
        float pitch = location.getPitch();
        if (pitch > 80) {
            currentYaw = location.getYaw();
        } else {
            currentYaw = (float) session.snapAngle(location.getYaw() + yawOffset - initialYaw) + initialYaw;
        }
    }

    @Override
    protected void apply() {
        bone.setPositionAndYaw(currentPosition, currentYaw);
    }
}
