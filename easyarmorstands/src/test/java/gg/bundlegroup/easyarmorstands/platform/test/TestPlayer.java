package gg.bundlegroup.easyarmorstands.platform.test;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
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
    private final Vector3d nextEyePosition = new Vector3d();
    private final Matrix3d nextEyeRotation = new Matrix3d();
    private final Set<EasEntity> hiddenEntities = new HashSet<>();

    public TestPlayer(TestPlatform platform, TestWorld world) {
        super(platform, world);
    }

    @Override
    public void move(Vector3dc position, float yaw, float pitch) {
        super.move(position, yaw, pitch);
        nextPosition.add(0.0, EYE_HEIGHT, 0.0, nextEyePosition);
        nextEyeRotation.rotationZYX(0, -Math.toRadians(yaw), Math.toRadians(pitch));
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
    public void update() {
        super.update();
        eyePosition.set(nextEyePosition);
        eyeRotation.set(nextEyeRotation);
    }

    @Override
    public Vector3dc getEyePosition() {
        assertUpToDate();
        return eyePosition;
    }

    @Override
    public Matrix3dc getEyeRotation() {
        assertUpToDate();
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
    public void giveTool() {
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
