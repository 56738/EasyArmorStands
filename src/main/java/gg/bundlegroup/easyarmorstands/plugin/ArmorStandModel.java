package gg.bundlegroup.easyarmorstands.plugin;

import gg.bundlegroup.easyarmorstands.math.Matrix3x3;
import gg.bundlegroup.easyarmorstands.math.Vector3;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class ArmorStandModel {
    public static final Matrix3x3 SWITCH_FORWARD_UP = new Matrix3x3(
            1, 0, 0,
            0, 0, -1,
            0, 1, 0
    );

    public static final Matrix3x3 SWITCH_FORWARD_DOWN = new Matrix3x3(
            1, 0, 0,
            0, 0, 1,
            0, -1, 0
    );

    public static Vector3 getBonePosition(ArmorStand entity, EasBoneType type) {
        Location location = entity.getLocation();
        Matrix3x3 yaw = Matrix3x3.rotateY(Math.toRadians(location.getYaw()));
        return new Vector3(location).add(yaw.multiply(type.offset(entity)));
    }

    public static Vector3 getBoneEnd(ArmorStand entity, EasBoneType type) {
        Location location = entity.getLocation();
        Matrix3x3 yaw = Matrix3x3.rotateY(Math.toRadians(location.getYaw()));
        Vector3 start = new Vector3(location).add(yaw.multiply(type.offset(entity)));
        Matrix3x3 rotation = yaw.multiply(new Matrix3x3(type.getPose(entity)));
        return start.add(rotation.multiply(type.length(entity)));
    }

    public static Bone getBone(ArmorStand entity, EasBoneType type) {
        Location location = entity.getLocation();
        Vector3 entityPosition = new Vector3(location);
        Matrix3x3 yaw = Matrix3x3.rotateY(Math.toRadians(location.getYaw()));
        Vector3 start = entityPosition.add(yaw.multiply(type.offset(entity)));
        Matrix3x3 rotation = yaw.multiply(new Matrix3x3(type.getPose(entity)));
        Vector3 end = start.add(rotation.multiply(type.length(entity)));
        return new Bone(rotation, start, end);
    }
}
