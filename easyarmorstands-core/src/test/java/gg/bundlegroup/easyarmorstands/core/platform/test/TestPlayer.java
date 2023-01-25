package gg.bundlegroup.easyarmorstands.core.platform.test;

import gg.bundlegroup.easyarmorstands.core.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.core.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlayer;
import net.kyori.adventure.util.RGBLike;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashSet;
import java.util.Set;

public class TestPlayer extends TestEntity implements EasPlayer {
    public static final double EYE_HEIGHT = 1.53;
    private final Vector3d eyePosition = new Vector3d();
    private final Matrix3d eyeRotation = new Matrix3d();
    private final Set<EasEntity> hiddenEntities = new HashSet<>();

    public TestPlayer(TestPlatform platform, TestWorld world) {
        super(platform, world);
    }

    @Override
    public void move(Vector3dc position, float yaw, float pitch) {
        super.move(position, yaw, pitch);
        position.add(0.0, EYE_HEIGHT, 0.0, eyePosition);
        eyeRotation.rotationZYX(0, -Math.toRadians(yaw), Math.toRadians(pitch));
    }

    public void move(Vector3dc position, Vector3dc direction) {
        double x = direction.x();
        double y = direction.y();
        double z = direction.z();
        double xz = Math.sqrt(x * x + z * z);
        double yaw = Math.atan2(-x, z);
        double pitch = Math.atan2(-y, xz);
        move(position, (float) Math.toDegrees(yaw), (float) Math.toDegrees(pitch));
    }

    @Override
    public Vector3dc getEyePosition() {
        return eyePosition;
    }

    @Override
    public Matrix3dc getEyeRotation() {
        return eyeRotation;
    }

    @Override
    public void hideEntity(EasEntity entity) {
        hiddenEntities.add(entity);
    }

    @Override
    public void showEntity(EasEntity entity) {
        hiddenEntities.remove(entity);
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public void giveTool() {
    }

    @Override
    public void lookAt(Vector3dc target) {
    }

    @Override
    public void showPoint(Vector3dc point, RGBLike color) {
    }

    @Override
    public void showLine(Vector3dc from, Vector3dc to, RGBLike color, boolean includeEnds) {
    }

    @Override
    public void showCircle(Vector3dc center, Vector3dc axis, RGBLike color, double radius) {
    }

    @Override
    public void openInventory(EasInventory inventory) {
    }

    @Override
    public void closeInventory(EasInventory inventory) {
    }

    @Override
    public void setCursor(EasItem item) {
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public EasItem getItem(Slot slot) {
        return null;
    }

    @Override
    public void setItem(Slot slot, EasItem item) {
    }
}
