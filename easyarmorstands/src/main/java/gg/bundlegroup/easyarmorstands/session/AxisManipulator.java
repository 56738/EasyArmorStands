package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.awt.*;

public class AxisManipulator {
    private final EasPlayer player;
    private final Vector3d origin = new Vector3d();
    private final Vector3d axis = new Vector3d();
    private final Matrix3d current = new Matrix3d();
    private final Quaterniond difference = new Quaterniond();
    private final Vector3d lastDirection = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d axisStart = new Vector3d();
    private final Vector3d axisPoint = new Vector3d();
    private final Vector3d axisEnd = new Vector3d();
    private Color axisColor;
    private double axisPos;

    public AxisManipulator(EasPlayer player) {
        this.player = player;
    }

    private void updateAxisPoint(Vector3dc cursor) {
        axisPos = cursor.sub(origin, axisPoint).dot(axis);
        origin.fma(axisPos, axis, axisPoint);
    }

    private void updateDirection(Vector3dc cursor, Vector3d dest) {
        updateAxisPoint(cursor);
        cursor.sub(axisPoint, dest).normalize();
    }

    public void start(Vector3dc cursor, Vector3dc origin, Vector3dc axis, Color color, Matrix3dc current) {
        origin.get(this.origin);
        current.transform(axis, this.axis).normalize();
        current.get(this.current);
        this.axisColor = color;
        updateDirection(cursor, lastDirection);
    }

    public Matrix3dc update(Vector3dc cursor) {
        updateDirection(cursor, currentDirection);
        double angle = lastDirection.angleSigned(currentDirection, axis);
        lastDirection.set(currentDirection);

        if (player.platform().canSpawnParticles()) {
            double axisMin = -2;
            double axisMax = 2;

            if (axisPos < axisMin) {
                axisMin = axisPos;
            }
            if (axisPos > axisMax) {
                axisMax = axisPos;
            }

            this.origin.fma(axisMin, this.axis, this.axisStart);
            this.origin.fma(axisMax, this.axis, this.axisEnd);

            player.showLine(axisStart, axisEnd, axisColor, true);
            player.showLine(axisPoint, cursor, Color.WHITE, false);
        }

        return current.rotateLocal(angle, axis.x, axis.y, axis.z);
    }
}
