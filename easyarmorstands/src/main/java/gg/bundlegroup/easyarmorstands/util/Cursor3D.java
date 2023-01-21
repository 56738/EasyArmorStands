package gg.bundlegroup.easyarmorstands.util;

import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor3D {
    private final EasPlayer player;

    private final Vector3d cursor = new Vector3d();
    private final Vector3d current = new Vector3d();

    public Cursor3D(EasPlayer player) {
        this.player = player;
    }

    public void start(Vector3dc cursor, boolean force) {
        if (force) {
            player.lookAt(cursor);
        }
        this.cursor.set(cursor);
        this.cursor.sub(player.getEyePosition());
        this.cursor.mulTranspose(player.getEyeRotation());
        this.current.set(cursor);
    }

    public void update() {
        player.getEyeRotation().transform(cursor, current).add(player.getEyePosition());
        player.showPoint(current, NamedTextColor.YELLOW);
    }

    public Vector3dc get() {
        return current;
    }
}
