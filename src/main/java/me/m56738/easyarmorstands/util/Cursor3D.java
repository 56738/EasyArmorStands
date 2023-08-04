package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor3D {
    private final Player player;
    private final Session session;

    private final Vector3d cursor = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor3D(Player player, Session session) {
        this.player = player;
        this.session = session;
    }

    public void start(Vector3dc cursor) {
        this.current.set(cursor);
        refresh();
    }

    private void refresh() {
        this.cursor.set(current);
        Location eyeLocation = player.getEyeLocation();
        this.cursor.sub(Util.toVector3d(eyeLocation));
        this.cursor.mulTranspose(Util.getRotation(eyeLocation, new Matrix3d()));
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            Location eyeLocation = player.getEyeLocation();
            Util.getRotation(eyeLocation, new Matrix3d()).transform(cursor, current).add(Util.toVector3d(eyeLocation));
        }
    }

    public Vector3dc get() {
        return current;
    }

    public void reset() {
        cursor.set(0, 0, 2);
        Location eyeLocation = player.getEyeLocation();
        Util.getRotation(eyeLocation, new Matrix3d()).transform(cursor, current).add(Util.toVector3d(eyeLocation));
    }
}
