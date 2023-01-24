package gg.bundlegroup.easyarmorstands.core.util;

import gg.bundlegroup.easyarmorstands.core.platform.EasPlayer;
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
        this.current.set(cursor);
        refresh();
    }

    private void refresh() {
        this.cursor.set(current);
        this.cursor.sub(player.getEyePosition());
        this.cursor.mulTranspose(player.getEyeRotation());
    }

    public void update(boolean freeLook) {
        if (freeLook) {
            refresh();
        } else {
            player.getEyeRotation().transform(cursor, current).add(player.getEyePosition());
        }
        player.showPoint(current, NamedTextColor.YELLOW);
    }

    public Vector3dc get() {
        return current;
    }
}
