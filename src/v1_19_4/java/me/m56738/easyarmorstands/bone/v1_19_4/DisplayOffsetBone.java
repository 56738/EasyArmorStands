package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3dc;

public class DisplayOffsetBone extends DisplayBone {
    private final Display entity;

    public DisplayOffsetBone(Session session, Display entity, DisplayAddon addon) {
        super(session, entity, addon);
        this.entity = entity;
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc oldPosition = getPosition();
        Matrix4d matrix = getTransformation();
        matrix.translateLocal(
                position.x() - oldPosition.x(),
                position.y() - oldPosition.y(),
                position.z() - oldPosition.z());
        setTransformation(matrix);
    }

    @Override
    public void setMatrix(Matrix4dc matrix) {
        Location location = entity.getLocation();
        setTransformation(matrix.translateLocal(
                -location.getX(),
                -location.getY(),
                -location.getZ(),
                new Matrix4d()));
    }
}
