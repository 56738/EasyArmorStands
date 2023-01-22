package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.PositionHandle;
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

public class PositionAxisManipulator implements Manipulator {
    private final PositionHandle handle;
    private final Session session;
    private final EasPlayer player;
    private final Component name;
    private final TextColor color;
    private final Vector3dc axis;

    private final Vector3d negativeHandle = new Vector3d();
    private final Vector3d positiveHandle = new Vector3d();
    private final Vector3d lookRayEnd = new Vector3d();
    private final Vector3d lookRayPoint = new Vector3d();
    private final Vector3d handlePoint = new Vector3d();
    private final Vector3d start = new Vector3d();
    private final Vector3d currentHandle = new Vector3d();
    private final Vector3d origin = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private final Cursor3D cursor;
    private Vector3dc lookTarget;

    public PositionAxisManipulator(PositionHandle handle, String name, RGBLike color, Vector3dc axis) {
        this.handle = handle;
        this.session = handle.session();
        this.player = handle.session().getPlayer();
        this.name = Component.text(name, TextColor.color(color));
        this.color = TextColor.color(color);
        this.axis = new Vector3d(axis);
        this.cursor = new Cursor3D(player);
    }

    @Override
    public void refresh() {
        handle.getPosition().fma(-2, axis, negativeHandle);
        handle.getPosition().fma(2, axis, positiveHandle);
        Vector3dc eyePosition = player.getEyePosition();
        player.getEyeRotation().transform(0, 0, session.getRange(), lookRayEnd).add(eyePosition);
        double d = Intersectiond.findClosestPointsLineSegments(
                eyePosition.x(), eyePosition.y(), eyePosition.z(),
                lookRayEnd.x(), lookRayEnd.y(), lookRayEnd.z(),
                negativeHandle.x, negativeHandle.y, negativeHandle.z,
                positiveHandle.x, positiveHandle.y, positiveHandle.z,
                lookRayPoint,
                handlePoint
        );
        double threshold = session.getLookThreshold();
        if (d < threshold * threshold) {
            lookTarget = lookRayPoint;
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
        double t = session.snap(cursor.get().sub(start, temp).dot(axis));
        start.fma(t, axis, currentHandle);
        EasArmorStand entity = session.getEntity();
        entity.teleport(origin.fma(t, axis, temp), entity.getYaw(), 0);
        handle.getPosition().fma(-2, axis, negativeHandle);
        handle.getPosition().fma(2, axis, positiveHandle);
        return Component.text(t, color);
    }

    @Override
    public void abort() {
        EasArmorStand entity = session.getEntity();
        entity.teleport(origin, entity.getYaw(), 0);
    }

    @Override
    public void showHandles() {
        player.showLine(negativeHandle, positiveHandle, color, true);
    }

    @Override
    public void show() {
        player.showLine(negativeHandle, positiveHandle, color, true);
        player.showLine(currentHandle, cursor.get(), NamedTextColor.WHITE, false);
    }

    @Override
    public Vector3dc getTarget() {
        return handle.getPosition();
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
