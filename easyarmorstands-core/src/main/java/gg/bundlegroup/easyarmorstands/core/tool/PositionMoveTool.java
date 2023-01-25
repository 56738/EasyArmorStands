package gg.bundlegroup.easyarmorstands.core.tool;

import gg.bundlegroup.easyarmorstands.core.bone.PositionBone;
import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.core.session.Session;
import gg.bundlegroup.easyarmorstands.core.util.Cursor3D;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Intersectiond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionMoveTool extends AbstractTool {
    private final PositionBone bone;
    private final Session session;
    private final EasPlayer player;
    private final Cursor3D cursor;
    private final Vector3d original = new Vector3d();
    private final Vector3d offset = new Vector3d();
    private final Vector3d current = new Vector3d();
    private final Vector3d lookRayEnd = new Vector3d();
    private final Vector3d lookRayPoint = new Vector3d();
    private float originalYaw;
    private double yOffset;
    private float yawOffset;
    private boolean looking;

    public PositionMoveTool(PositionBone bone, String name, RGBLike color) {
        super(Component.text(name, TextColor.color(color)), Component.text("Moving armor stand"));
        this.bone = bone;
        this.session = bone.getSession();
        this.player = session.getPlayer();
        this.cursor = new Cursor3D(this.player);
    }

    @Override
    public void refresh() {
        Vector3dc handle = bone.getPosition();
        Vector3dc eye = player.getEyePosition();
        player.getEyeRotation().transform(0, 0, session.getRange(), lookRayEnd).add(eye);
        Intersectiond.findClosestPointOnLineSegment(
                eye.x(), eye.y(), eye.z(),
                lookRayEnd.x(), lookRayEnd.y(), lookRayEnd.z(),
                handle.x(), handle.y(), handle.z(),
                lookRayPoint);
        double threshold = session.getLookThreshold();
        looking = lookRayPoint.distanceSquared(handle) < threshold * threshold;
    }

    @Override
    public void start(Vector3dc cursor) {
        this.cursor.start(cursor, false);
        EasArmorStand entity = session.getEntity();
        original.set(entity.getPosition());
        originalYaw = entity.getYaw();
        original.sub(cursor, offset);
        yOffset = original.y - player.getPosition().y();
        yawOffset = originalYaw - player.getYaw();
    }

    @Override
    public Component update() {
        cursor.update(false);
        float yaw;
        if (player.getPitch() > 80) {
            current.set(player.getPosition());
            yaw = player.getYaw();
        } else {
            cursor.get().add(offset, current);
            current.y = player.getPosition().y() + yOffset;
            yaw = player.getYaw() + yawOffset;
        }
        session.move(current, yaw);
        return null;
    }

    @Override
    public void abort() {
        session.move(original, originalYaw);
    }

    @Override
    public void showHandles() {
        player.showPoint(bone.getPosition(), NamedTextColor.YELLOW);
    }

    @Override
    public void show() {
    }

    @Override
    public Vector3dc getTarget() {
        return bone.getPosition();
    }

    @Override
    public Vector3dc getLookTarget() {
        return looking ? lookRayPoint : null;
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
