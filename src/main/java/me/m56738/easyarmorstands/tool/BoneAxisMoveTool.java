package me.m56738.easyarmorstands.tool;

import me.m56738.easyarmorstands.bone.PartBone;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.joml.Intersectiond;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneAxisMoveTool extends AbstractTool {
    private final PartBone bone;
    private final ArmorStandSession session;
    private final Player player;
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

    public BoneAxisMoveTool(PartBone bone, String name, RGBLike color, Vector3dc axis) {
        super(Component.text(name, TextColor.color(color)), Component.text("Move along bone axis"));
        this.bone = bone;
        this.session = bone.getSession();
        this.player = session.getPlayer();
        this.color = TextColor.color(color);
        this.axis = new Vector3d(axis);
        this.cursor = new Cursor3D(player, session);
    }

    @Override
    public void refresh() {
        bone.getRotation().transform(axis, direction);
        bone.getAnchor().fma(-2, direction, negativeHandle);
        bone.getAnchor().fma(2, direction, positiveHandle);
        Location eyeLocation = player.getEyeLocation();
        Vector3dc eyePosition = Util.toVector3d(eyeLocation);
        Util.getRotation(eyeLocation, new Matrix3d()).transform(0, 0, session.getRange(), lookRayEnd).add(eyePosition);
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
        this.cursor.start(cursor);
        start.set(cursor);
        origin.set(Util.toVector3d(session.getEntity().getLocation()));
    }

    @Override
    public Component update() {
        cursor.update(false);
        double t = session.snap(cursor.get().sub(start, temp).dot(direction));
        origin.fma(t, direction, temp);
        if (!session.move(temp)) {
            return null;
        }
        start.fma(t, direction, currentHandle);
        bone.refresh();
        bone.getAnchor().fma(-2, direction, negativeHandle);
        bone.getAnchor().fma(2, direction, positiveHandle);
        return Component.text(Util.OFFSET_FORMAT.format(t), color);
    }

    @Override
    public void abort() {
        ArmorStand entity = session.getEntity();
        entity.teleport(new Location(entity.getWorld(), origin.x, origin.y, origin.z, entity.getLocation().getYaw(), 0));
    }

    @Override
    public void showHandles() {
        session.showPoint(negativeHandle, color);
        session.showPoint(positiveHandle, color);
    }

    @Override
    public void show() {
        session.showLine(negativeHandle, positiveHandle, color, true);
        session.showLine(currentHandle, cursor.get(), NamedTextColor.WHITE, false);
    }

    @Override
    public Vector3dc getTarget() {
        return bone.getAnchor();
    }

    private void updateLookRayPoint(Vector3dc handle, Vector3d dest) {
        Vector3dc eye = Util.toVector3d(player.getEyeLocation());
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
}
