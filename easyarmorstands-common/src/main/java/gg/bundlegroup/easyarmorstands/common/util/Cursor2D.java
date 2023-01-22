package gg.bundlegroup.easyarmorstands.common.util;

import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor2D {
    private final EasPlayer player;

    private final Vector3d origin = new Vector3d();
    private final Vector3d normal = new Vector3d();
    private final Vector3d cursor = new Vector3d();
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
        this.origin.set(origin);
        this.current.set(cursor);
        this.normal.set(normal);
        refresh();
    }

    private void refresh() {
        current.sub(player.getEyePosition(), cursor).mulTranspose(player.getEyeRotation());
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            player.getEyeRotation().transform(cursor.x, cursor.y, 0, currentOrigin).add(player.getEyePosition());
            player.getEyeRotation().transform(0, 0, 1, currentDirection);
            double t = Util.intersectRayDoubleSidedPlane(currentOrigin, currentDirection, origin, normal);
            if (t < 0) {
                return;
            }
            currentOrigin.fma(t, currentDirection, current);
        }
        player.showPoint(current, NamedTextColor.YELLOW);
    }

    public Vector3dc get() {
        return current;
    }
}
