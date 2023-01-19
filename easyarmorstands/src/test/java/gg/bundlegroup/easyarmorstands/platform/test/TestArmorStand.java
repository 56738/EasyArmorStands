package gg.bundlegroup.easyarmorstands.platform.test;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.EnumMap;

public class TestArmorStand extends TestEntity implements EasArmorStand {
    private final EnumMap<Part, Vector3d> poses = new EnumMap<>(Part.class);
    private boolean visible = true;
    private boolean basePlate = true;
    private boolean arms;
    private boolean gravity = true;
    private boolean small;
    private float yaw;

    public TestArmorStand(TestPlatform platform, TestWorld world) {
        super(platform, world);
        for (Part part : Part.values()) {
            poses.put(part, new Vector3d());
        }
    }

    @Override
    public void move(Vector3dc position, float yaw, float pitch) {
        super.move(position, yaw, pitch);
        this.yaw = yaw;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;
    }

    @Override
    public void setArms(boolean arms) {
        this.arms = arms;
    }

    @Override
    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    @Override
    public boolean isSmall() {
        return small;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public Vector3d getPose(Part part, Vector3d dest) {
        poses.get(part).get(dest);
        return dest;
    }

    @Override
    public void setPose(Part part, Vector3dc pose) {
        poses.get(part).set(pose);
    }
}
