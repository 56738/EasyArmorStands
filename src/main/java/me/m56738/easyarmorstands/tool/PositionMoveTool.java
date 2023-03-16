package me.m56738.easyarmorstands.tool;

import me.m56738.easyarmorstands.bone.PositionBone;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3dc;

public class PositionMoveTool extends AbstractTool {
//    private final PositionBone bone;
//    private final ArmorStandSession session;
//    private final Player player;
//    private final Cursor3D cursor;
//    private final Vector3d original = new Vector3d();
//    private final Vector3d originalCursor = new Vector3d();
//    private final Vector3d offset = new Vector3d();
//    private final Vector3d current = new Vector3d();
//    private final Vector3d lookRayEnd = new Vector3d();
//    private final Vector3d lookRayPoint = new Vector3d();
//    private float originalYaw;
//    private double yOffset;
//    private float yawOffset;
//    private boolean looking;

    public PositionMoveTool(PositionBone bone, String name, RGBLike color) {
        super(Component.text(name, TextColor.color(color)), Component.text("Moving armor stand"));
//        this.bone = bone;
//        this.session = bone.getSession();
//        this.player = session.getPlayer();
//        this.cursor = new Cursor3D(this.player, session);
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException();
//        Vector3dc handle = bone.getPosition();
//        Location eyeLocation = player.getEyeLocation();
//        Vector3dc eyePosition = Util.toVector3d(eyeLocation);
//        Util.getRotation(eyeLocation, new Matrix3d()).transform(0, 0, session.getRange(), lookRayEnd).add(eyePosition);
//        Intersectiond.findClosestPointOnLineSegment(
//                eyePosition.x(), eyePosition.y(), eyePosition.z(),
//                lookRayEnd.x(), lookRayEnd.y(), lookRayEnd.z(),
//                handle.x(), handle.y(), handle.z(),
//                lookRayPoint);
//        double threshold = session.getLookThreshold();
//        looking = lookRayPoint.distanceSquared(handle) < threshold * threshold;
    }

    @Override
    public void start(Vector3dc cursor) {
        throw new UnsupportedOperationException();
//        this.cursor.start(cursor);
//        ArmorStand entity = session.getEntity();
//        Location location = entity.getLocation();
//        original.set(Util.toVector3d(location));
//        originalYaw = location.getYaw();
//        original.sub(cursor, offset);
//        originalCursor.set(cursor);
//        Location playerLocation = player.getLocation();
//        yOffset = original.y - playerLocation.getY();
//        yawOffset = originalYaw - playerLocation.getYaw();
    }

    @Override
    public Component update() {
        throw new UnsupportedOperationException();
//        cursor.update(false);
//        float yaw;
//        Location playerLocation = player.getLocation();
//        if (playerLocation.getPitch() > 80) {
//            current.set(Util.toVector3d(playerLocation));
//            yaw = playerLocation.getYaw();
//        } else {
//            Vector3dc cursor = this.cursor.get();
//            current.x = session.snap(cursor.x() - originalCursor.x) + originalCursor.x + offset.x;
//            current.y = session.snap(cursor.y() - originalCursor.y) + originalCursor.y + offset.y;
//            current.z = session.snap(cursor.z() - originalCursor.z) + originalCursor.z + offset.z;
//            if (!player.isFlying()) {
//                current.y = playerLocation.getY() + yOffset;
//            }
//            yaw = (float) session.snapAngle(playerLocation.getYaw() + yawOffset - originalYaw) + originalYaw;
//        }
//        session.move(current, yaw);
//        return null;
    }

    @Override
    public void abort() {
        throw new UnsupportedOperationException();
//        session.move(original, originalYaw);
    }

    @Override
    public void showHandles() {
        throw new UnsupportedOperationException();
//        session.showPoint(bone.getPosition(), NamedTextColor.YELLOW);
    }

    @Override
    public void show() {
    }

    @Override
    public Vector3dc getTarget() {
        throw new UnsupportedOperationException();
//        return bone.getPosition();
    }

    @Override
    public Vector3dc getLookTarget() {
        throw new UnsupportedOperationException();
//        return looking ? lookRayPoint : null;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
