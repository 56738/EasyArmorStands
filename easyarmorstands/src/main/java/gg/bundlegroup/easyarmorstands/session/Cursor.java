package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Cursor {
    private final EasPlayer player;

    private final Vector3d origin = new Vector3d();
    private final Vector3d cursor = new Vector3d();

    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d lastDirection = new Vector3d();
    private final Vector3d up = new Vector3d();
    private final Quaterniond difference = new Quaterniond();
    private final Matrix3d current = new Matrix3d();

    public Cursor(EasPlayer player) {
        this.player = player;
    }

    private void updateDirection() {
        player.getEyeRotation().transform(cursor, currentDirection).add(player.getEyePosition()).sub(origin);
    }

    public void start(Vector3dc origin, Vector3dc cursor, Matrix3dc current) {
        this.origin.set(origin);
        this.cursor.set(cursor);
        this.cursor.sub(player.getEyePosition());
        this.cursor.mulTranspose(player.getEyeRotation());
        this.current.set(current);
        updateDirection();
        lastDirection.set(currentDirection);
    }

    public Matrix3dc update() {
        updateDirection();
        lastDirection.rotationTo(currentDirection, difference);
        lastDirection.set(currentDirection);
        return current.rotateLocal(difference);
    }

    public void look(Vector3dc direction) {
        current.transform(0, 1, 0, up);
        current.rotationTowards(up, direction).mapnXZY();
    }
}
