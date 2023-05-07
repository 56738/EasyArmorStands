package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.MatrixBone;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTransformationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Math;
import org.joml.*;

public class DisplayBone implements MatrixBone {
    private final Session session;
    private final Display entity;
    private final JOMLMapper mapper;
    private final DisplayTransformationProperty transformationProperty;

    public DisplayBone(Session session, Display entity, JOMLMapper mapper, DisplayTransformationProperty transformationProperty) {
        this.session = session;
        this.entity = entity;
        this.mapper = mapper;
        this.transformationProperty = transformationProperty;
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
        session.setProperty(entity, EasyArmorStands.getInstance().getEntityLocationProperty(), location);
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
        session.setProperty(entity, EasyArmorStands.getInstance().getEntityLocationProperty(), location);
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
        session.setProperty(entity, transformationProperty, mapper.getTransformation(new Matrix4f(transformation)));
    }
}
