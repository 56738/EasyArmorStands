package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.PositionAndYawBone;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
        yawOffset = initialYaw - session.getPlayer().getLocation().getYaw();
    }

    @Override
    protected void abort() {
        bone.setPositionAndYaw(initialPosition, initialYaw);
    }

    @Override
    protected void update() {
        super.update();
        Player player = session.getPlayer();
        Location location = player.getLocation();
        if (location.getPitch() > 80) {
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
