package me.m56738.easyarmorstands.core.platform.test;

import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasItem;
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
    private boolean locked;

    public TestArmorStand(TestPlatform platform, TestWorld world) {
        super(platform, world);
        for (Part part : Part.values()) {
            poses.put(part, new Vector3d());
        }
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean hasBasePlate() {
        return basePlate;
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        this.basePlate = basePlate;
    }

    @Override
    public boolean hasArms() {
        return arms;
    }

    @Override
    public void setArms(boolean arms) {
        this.arms = arms;
    }

    @Override
    public boolean hasGravity() {
        return gravity;
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
    public void setSmall(boolean small) {
        this.small = small;
    }

    @Override
    public boolean canTick() {
        return true;
    }

    @Override
    public void setCanTick(boolean canTick) {
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

    @Override
    public EasItem getItem(Slot slot) {
        return null;
    }

    @Override
    public void setItem(Slot slot, EasItem item) {
    }
}
