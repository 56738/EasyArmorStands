package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.ArmorStandPositionBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CarryNode extends EditNode {
    private final Session session;
    private final ArmorStandPositionBone bone;
    private final Vector3d initialPosition = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentPosition = new Vector3d();
    private final Cursor3D cursor;
    private float initialYaw;
    private float yawOffset;

    public CarryNode(Session session, ArmorStandPositionBone bone) {
        super(session);
        this.session = session;
        this.bone = bone;
        this.cursor = new Cursor3D(session.getPlayer(), session);
    }

    @Override
    public void onEnter() {
        Location location = session.getPlayer().getLocation();
        initialPosition.set(bone.getPosition());
        initialPosition.sub(Util.toVector3d(location), initialOffset);
        initialYaw = bone.getYaw();
        yawOffset = initialYaw - location.getYaw();
        cursor.start(initialPosition);
    }

    @Override
    protected void abort() {
        bone.setPositionAndYaw(initialPosition, initialYaw);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        cursor.update(false);
        Player player = session.getPlayer();
        Location location = player.getLocation();
        float yaw;
        if (location.getPitch() > 80) {
            currentPosition.set(location.getX(), location.getY(), location.getZ()).add(bone.getOffset());
            yaw = location.getYaw();
        } else {
            Vector3dc cursor = this.cursor.get();
            currentPosition.x = session.snap(cursor.x() - initialPosition.x) + initialPosition.x;
            currentPosition.y = session.snap(cursor.y() - initialPosition.y) + initialPosition.y;
            currentPosition.z = session.snap(cursor.z() - initialPosition.z) + initialPosition.z;
            if (!player.isFlying()) {
                currentPosition.y = location.getY() + initialOffset.y;
            }
            yaw = (float) session.snapAngle(location.getYaw() + yawOffset - initialYaw) + initialYaw;
        }
        bone.setPositionAndYaw(currentPosition, yaw);
    }

    @Override
    public boolean isValid() {
        return super.isValid() && bone.isValid();
    }
}
