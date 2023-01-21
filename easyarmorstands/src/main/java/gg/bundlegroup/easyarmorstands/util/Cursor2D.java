package gg.bundlegroup.easyarmorstands.util;

import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Intersectiond;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor2D {
    private final EasPlayer player;

    private final Vector3d origin = new Vector3d();
    private final Vector3d normal = new Vector3d();
    private final Vector2d cursor = new Vector2d();
    private final Vector3d currentOrigin = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor2D(EasPlayer player) {
        this.player = player;
    }

    public void start(Vector3dc origin, Vector3dc cursor, Vector3dc normal, boolean force) {
        if (force) {
            player.lookAt(cursor);
        }
        Vector3dc localCursor = cursor.sub(player.getEyePosition(), new Vector3d())
                .mulTranspose(player.getEyeRotation());
        this.cursor.set(localCursor.x(), localCursor.y());
        this.origin.set(origin);
        this.current.set(cursor);
        this.normal.set(normal);
    }

    public void update() {
        player.getEyeRotation().transform(cursor.x, cursor.y, 0, currentOrigin).add(player.getEyePosition());
        player.getEyeRotation().transform(0, 0, 1, currentDirection);
        double t = Intersectiond.intersectRayPlane(currentOrigin, currentDirection, origin, normal, 0.01);
        if (t < 0) {
            normal.negate();
            t = Intersectiond.intersectRayPlane(currentOrigin, currentDirection, origin, normal, 0.01);
            if (t < 0) {
                return;
            }
        }
        currentOrigin.fma(t, currentDirection, current);
        player.showPoint(current, NamedTextColor.YELLOW);
    }

    public Vector3dc get() {
        return current;
    }
}
