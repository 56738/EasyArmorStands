package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.bone.MatrixBone;
import me.m56738.easyarmorstands.session.v1_19_4.DisplaySession;
import org.bukkit.Location;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayBone implements MatrixBone {
    private final DisplaySession session;

    public DisplayBone(DisplaySession session) {
        this.session = session;
    }

    @Override
    public Vector3dc getPosition() {
        return getMatrix().getTranslation(new Vector3d());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc oldPosition = getPosition();
        Location location = session.getEntity().getLocation();
        location.add(
                position.x() - oldPosition.x(),
                position.y() - oldPosition.y(),
                position.z() - oldPosition.z());
        session.getEntity().teleport(location);
    }

    @Override
    public Matrix4d getMatrix() {
        Location location = session.getEntity().getLocation();
        Matrix4d matrix = new Matrix4d()
                .translation(location.getX(), location.getY(), location.getZ())
                .rotateY(-Math.toRadians(location.getYaw()));
        return matrix.mul(session.getTransformation());
    }

    @Override
    public void setMatrix(Matrix4dc matrix) {
        Vector3d position = matrix.getTranslation(new Vector3d());
        Vector3dc oldPosition = getPosition();
        Location location = session.getEntity().getLocation();
        location.add(
                position.x() - oldPosition.x(),
                position.y() - oldPosition.y(),
                position.z() - oldPosition.z());
        session.getEntity().teleport(location);
        session.setTransformation(matrix.translateLocal(
                -location.getX(),
                -location.getY(),
                -location.getZ(),
                new Matrix4d()));
    }
}
