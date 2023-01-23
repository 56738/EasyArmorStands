package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.bone.PartBone;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.util.Cursor3D;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Intersectiond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneAxisMoveManipulator implements Manipulator {
    private final PartBone bone;
    private final Session session;
    private final EasPlayer player;
    private final Component name;
    private final TextColor color;
    private final Vector3dc axis;

    private final Vector3d direction = new Vector3d();
    private final Vector3d negativeHandle = new Vector3d();
    private final Vector3d positiveHandle = new Vector3d();
    private final Vector3d lookRayEnd = new Vector3d();
    private final Vector3d negativeLookRayPoint = new Vector3d();
    private final Vector3d positiveLookRayPoint = new Vector3d();
    private final Vector3d start = new Vector3d();
    private final Vector3d currentHandle = new Vector3d();
    private final Vector3d origin = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private final Cursor3D cursor;
    private Vector3dc lookTarget;

    public BoneAxisMoveManipulator(PartBone bone, String name, RGBLike color, Vector3dc axis) {
        this.bone = bone;
        this.session = bone.session();
        this.player = bone.session().getPlayer();
        this.name = Component.text(name, TextColor.color(color));
        this.color = TextColor.color(color);
        this.axis = new Vector3d(axis);
        this.cursor = new Cursor3D(player);
    }

    @Override
    public void refresh() {
        bone.getRotation().transform(axis, direction);
        bone.getAnchor().fma(-2, direction, negativeHandle);
        bone.getAnchor().fma(2, direction, positiveHandle);
        Vector3dc eyePosition = player.getEyePosition();
        player.getEyeRotation().transform(0, 0, session.getRange(), lookRayEnd).add(eyePosition);
        updateLookRayPoint(negativeHandle, negativeLookRayPoint);
        updateLookRayPoint(positiveHandle, positiveLookRayPoint);
        double threshold = session.getLookThreshold();
        boolean lookingAtNegative = negativeLookRayPoint.distanceSquared(negativeHandle) < threshold * threshold;
        boolean lookingAtPositive = positiveLookRayPoint.distanceSquared(positiveHandle) < threshold * threshold;
        if (lookingAtNegative && lookingAtPositive) {
            if (negativeLookRayPoint.distanceSquared(eyePosition) < positiveLookRayPoint.distanceSquared(eyePosition)) {
                lookTarget = negativeLookRayPoint;
            } else {
                lookTarget = positiveLookRayPoint;
            }
        } else if (lookingAtNegative) {
            lookTarget = negativeLookRayPoint;
        } else if (lookingAtPositive) {
            lookTarget = positiveLookRayPoint;
        } else {
            lookTarget = null;
        }
    }

    @Override
    public void start(Vector3dc cursor) {
        this.cursor.start(cursor, false);
        start.set(cursor);
        session.getEntity().getPosition().get(origin);
    }

    @Override
    public Component update() {
        cursor.update(false);
        double t = session.snap(cursor.get().sub(start, temp).dot(direction));
        EasArmorStand entity = session.getEntity();
        origin.fma(t, direction, temp);
        if (!session.canMove(temp)) {
            return null;
        }
        if (!entity.teleport(temp, entity.getYaw(), 0)) {
            return null;
        }
        start.fma(t, direction, currentHandle);
        bone.refresh();
        bone.getAnchor().fma(-2, direction, negativeHandle);
        bone.getAnchor().fma(2, direction, positiveHandle);
        return Component.text(t, color);
    }

    @Override
    public void abort() {
        EasArmorStand entity = session.getEntity();
        entity.teleport(origin, entity.getYaw(), 0);
    }

    @Override
    public void showHandles() {
        player.showPoint(negativeHandle, color);
        player.showPoint(positiveHandle, color);
    }

    @Override
    public void show() {
        player.showLine(negativeHandle, positiveHandle, color, true);
        player.showLine(currentHandle, cursor.get(), NamedTextColor.WHITE, false);
    }

    @Override
    public Vector3dc getTarget() {
        return bone.getAnchor();
    }

    private void updateLookRayPoint(Vector3dc handle, Vector3d dest) {
        Vector3dc eye = player.getEyePosition();
        Intersectiond.findClosestPointOnLineSegment(
                eye.x(), eye.y(), eye.z(),
                lookRayEnd.x(), lookRayEnd.y(), lookRayEnd.z(),
                handle.x(), handle.y(), handle.z(),
                dest);
    }

    @Override
    public Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public Component component() {
        return name;
    }
}
