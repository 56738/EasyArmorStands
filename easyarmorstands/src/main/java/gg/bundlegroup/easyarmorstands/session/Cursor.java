package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.awt.*;

public class Cursor {
    private final EasPlayer player;

    private final Vector3d cursor = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor(EasPlayer player) {
        this.player = player;
    }

    public void start(Vector3dc cursor, boolean force) {
        if (force) {
            player.lookAt(cursor);
        }
        this.cursor.set(cursor);
        this.cursor.sub(player.getEyePosition());
        this.cursor.mulTranspose(player.getEyeRotation());
    }

    public Vector3dc get() {
        player.getEyeRotation().transform(cursor, current).add(player.getEyePosition());
        player.showPoint(current, Color.YELLOW);
        return current;
    }
}
