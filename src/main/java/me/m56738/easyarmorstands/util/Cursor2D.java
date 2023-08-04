package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor2D {
    private final Player player;
    private final Session session;

    private final Vector3d origin = new Vector3d();
    private final Vector3d normal = new Vector3d();
    private final Vector3d cursor = new Vector3d();
    private final Vector3d currentOrigin = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor2D(Player player, Session session) {
        this.player = player;
        this.session = session;
    }

    public void start(Vector3dc origin, Vector3dc cursor, Vector3dc normal) {
        this.origin.set(origin);
        this.current.set(cursor);
        this.normal.set(normal);
        refresh();
    }

    private void refresh() {
        Location eyeLocation = player.getEyeLocation();
        current.sub(Util.toVector3d(eyeLocation), cursor).mulTranspose(Util.getRotation(eyeLocation, new Matrix3d()));
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            Location eyeLocation = player.getEyeLocation();
            Matrix3dc rotation = Util.getRotation(eyeLocation, new Matrix3d());
            rotation.transform(cursor.x, cursor.y, 0, currentOrigin).add(Util.toVector3d(eyeLocation));
            rotation.transform(0, 0, 1, currentDirection);
            double t = Util.intersectRayDoubleSidedPlane(currentOrigin, currentDirection, origin, normal);
            if (t < 0) {
                return;
            }
            currentOrigin.fma(t, currentDirection, current);
        }
    }

    public Vector3dc get() {
        return current;
    }
}
