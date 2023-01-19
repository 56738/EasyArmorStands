package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.awt.*;

public class AimManipulator {
    private final EasPlayer player;
    private final Vector3d origin = new Vector3d();

    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d lastDirection = new Vector3d();
    private final Vector3d up = new Vector3d();
    private final Quaterniond difference = new Quaterniond();
    private final Matrix3d current = new Matrix3d();

    public AimManipulator(EasPlayer player) {
        this.player = player;
    }

    private void updateDirection(Vector3dc cursor) {
        cursor.sub(origin, currentDirection);
    }

    public void start(Vector3dc cursor, Vector3dc origin, Matrix3dc current) {
        this.origin.set(origin);
        this.current.set(current);
        updateDirection(cursor);
        lastDirection.set(currentDirection);
    }

    public Matrix3dc update(Vector3dc cursor) {
        updateDirection(cursor);
        lastDirection.rotationTo(currentDirection, difference);
        lastDirection.set(currentDirection);
        player.showLine(origin, cursor, Color.WHITE, false);
        return current.rotateLocal(difference);
    }

    public void look(Vector3dc direction) {
        current.transform(0, 1, 0, up);
        current.rotationTowards(up, direction).mapnXZY();
    }
}
