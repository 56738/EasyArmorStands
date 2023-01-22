package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.BoneHandle;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.util.Cursor3D;
import net.kyori.adventure.util.RGBLike;
import org.joml.Intersectiond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneAxisMoveManipulator extends AxisManipulator {
    private final BoneHandle handle;
    private final EasPlayer player;
    private final Vector3dc axis;
    private final Cursor3D cursor;
    private final Vector3d pos = new Vector3d();
    private final Vector3d neg = new Vector3d();
    private final Vector3d offset = new Vector3d();
    private final Vector3d position = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();

    public BoneAxisMoveManipulator(BoneHandle handle, String name, RGBLike color, Vector3dc axis) {
        super(handle, name, color, color);
        this.handle = handle;
        this.player = handle.getSession().getPlayer();
        this.axis = new Vector3d(axis);
        this.cursor = new Cursor3D(player);
    }

    @Override
    public void start(Vector3dc cursor) {
        handle.getRotation().transform(axis, getAxis()).normalize();
        getOrigin().set(handle.getAnchor());
        this.cursor.start(cursor, false);
        updateAxisPoint(cursor);
        handle.getSession().getEntity().getPosition().sub(getAxisPoint(), offset);
    }

    @Override
    public void update(boolean active) {
        handle.getRotation().transform(axis, getAxis()).normalize();
        getOrigin().set(handle.getAnchor());
        getOrigin().fma(2, getAxis(), pos);
        getOrigin().fma(-2, getAxis(), neg);
        if (active) {
            cursor.update(false);
            super.update(true);
            EasArmorStand entity = handle.getSession().getEntity();
            float yaw = entity.getYaw();
            getAxisPoint().add(offset, position);
            entity.teleport(position, yaw, 0);
        } else {
            player.showPoint(pos, color());
            player.showPoint(neg, color());
        }
    }

    private Vector3dc getTarget(Vector3dc p) {
        Vector3dc eye = player.getEyePosition();
        Vector3dc pos = Intersectiond.findClosestPointOnLineSegment(
                eye.x(), eye.y(), eye.z(),
                eye.x() + 5 * currentDirection.x,
                eye.y() + 5 * currentDirection.y,
                eye.z() + 5 * currentDirection.z,
                p.x(), p.y(), p.z(),
                new Vector3d()
        );
        if (pos.distanceSquared(p) > 0.1 * 0.1) {
            return null;
        }
        return pos;
    }

    @Override
    public Vector3dc getLookTarget() {
        player.getEyeRotation().transform(0, 0, 1, currentDirection);
        Vector3dc t1 = getTarget(pos);
        Vector3dc t2 = getTarget(neg);
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        if (t1.distanceSquared(player.getEyePosition()) < t2.distanceSquared(player.getEyePosition())) {
            return t1;
        } else {
            return t2;
        }
    }

    @Override
    public Vector3dc getCursor() {
        return cursor.get();
    }
}
