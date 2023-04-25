package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.bone.MatrixBone;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Math;
import org.joml.*;

public class DisplayBone implements MatrixBone {
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayBone(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public Vector3dc getPosition() {
        return getMatrix().getTranslation(new Vector3d());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc oldPosition = getPosition();
        Location location = entity.getLocation();
        location.add(
                position.x() - oldPosition.x(),
                position.y() - oldPosition.y(),
                position.z() - oldPosition.z());
        entity.teleport(location);
    }

    @Override
    public Matrix4d getMatrix() {
        Location location = entity.getLocation();
        Matrix4d matrix = new Matrix4d()
                .translation(location.getX(), location.getY(), location.getZ())
                .rotateY(-Math.toRadians(location.getYaw()));
        return matrix.mul(getTransformation());
    }

    @Override
    public void setMatrix(Matrix4dc matrix) {
        Vector3d position = matrix.getTranslation(new Vector3d());
        Vector3dc oldPosition = getPosition();
        Location location = entity.getLocation();
        location.add(
                position.x() - oldPosition.x(),
                position.y() - oldPosition.y(),
                position.z() - oldPosition.z());
        entity.teleport(location);
        setTransformation(matrix.translateLocal(
                -location.getX(),
                -location.getY(),
                -location.getZ(),
                new Matrix4d()));
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    public Matrix4d getTransformation() {
        return new Matrix4d(mapper.getMatrix(entity.getTransformation()));
    }

    public void setTransformation(Matrix4dc transformation) {
        entity.setTransformation(mapper.getTransformation(new Matrix4f(transformation)));
    }
}
