package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.joml.Math;
import org.joml.*;

public class ArmorStandPartBone implements MatrixBone {
    private final ArmorStand armorStand;
    private final ArmorStandPart part;

    public ArmorStandPartBone(ArmorStand armorStand, ArmorStandPart part) {
        this.armorStand = armorStand;
        this.part = part;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public ArmorStandPart getPart() {
        return part;
    }

    @Override
    public Matrix4dc getMatrix() {
        Location location = armorStand.getLocation();
        EulerAngle angle = part.getPose(armorStand);
        return new Matrix4d()
                .translation(location.getX(), location.getY(), location.getZ())
                .rotateY(-Math.toRadians(location.getYaw()))
                .translate(part.getOffset(armorStand))
                .rotateZYX(-angle.getZ(), -angle.getY(), angle.getX());
    }

    @Override
    public void setMatrix(Matrix4dc matrix) {
        Location location = armorStand.getLocation();
        float rotY = -Math.toRadians(location.getYaw());

        Vector3d translation = matrix.getTranslation(new Vector3d());
        translation.sub(part.getOffset(armorStand).rotateY(rotY, new Vector3d()));
        location.setX(translation.x);
        location.setY(translation.y);
        location.setZ(translation.z);
        armorStand.teleport(location);

        Matrix3d rotation = matrix.get3x3(new Matrix3d()).rotateLocalY(-rotY);
        part.setPose(armorStand, Util.toEuler(rotation));
    }

    @Override
    public boolean isValid() {
        return armorStand.isValid();
    }

    @Override
    public Vector3dc getPosition() {
        Location location = armorStand.getLocation();
        return part.getOffset(armorStand)
                .rotateY(-Math.toRadians(location.getYaw()), new Vector3d())
                .add(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Location location = armorStand.getLocation();
        float rotY = -Math.toRadians(location.getYaw());
        Vector3d rotatedOffset = part.getOffset(armorStand).rotateY(rotY, new Vector3d());
        location.setX(position.x() - rotatedOffset.x);
        location.setY(position.y() - rotatedOffset.y);
        location.setZ(position.z() - rotatedOffset.z);
        armorStand.teleport(location);
    }

    public Vector3dc getEnd() {
        return getMatrix().translate(part.getLength(armorStand), new Matrix4d()).getTranslation(new Vector3d());
    }
}
