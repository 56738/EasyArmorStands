package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class CarryNode extends EditNode {
    protected final Session session;
    protected final PositionBone bone;
    protected final Vector3d initialPosition = new Vector3d();
    protected final Vector3d initialOffset = new Vector3d();
    protected final Vector3d currentPosition = new Vector3d();
    protected final Cursor3D cursor;

    public CarryNode(Session session, PositionBone bone) {
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
        cursor.start(initialPosition);
    }

    @Override
    protected void abort() {
        bone.setPosition(initialPosition);
    }

    protected void update() {
        cursor.update(false);
        Player player = session.getPlayer();
        Location location = player.getLocation();
        if (location.getPitch() > 80) {
            currentPosition.set(location.getX(), location.getY(), location.getZ());
        } else {
            Vector3dc cursor = this.cursor.get();
            currentPosition.x = session.snap(cursor.x() - initialPosition.x) + initialPosition.x;
            currentPosition.y = session.snap(cursor.y() - initialPosition.y) + initialPosition.y;
            currentPosition.z = session.snap(cursor.z() - initialPosition.z) + initialPosition.z;
            if (!player.isFlying()) {
                currentPosition.y = location.getY() + initialOffset.y;
            }
        }
    }

    protected void apply() {
        bone.setPosition(currentPosition);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        update();
        apply();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && bone.isValid();
    }
}
