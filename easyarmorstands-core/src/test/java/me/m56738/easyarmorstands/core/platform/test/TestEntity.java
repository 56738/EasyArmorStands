package me.m56738.easyarmorstands.core.platform.test;

import me.m56738.easyarmorstands.core.platform.EasEntity;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class TestEntity extends TestWrapper implements EasEntity {
    private final TestWorld world;
    private final Vector3d position = new Vector3d();
    private float yaw;
    private float pitch;
    private boolean persistent = true;
    private boolean glowing;
    private boolean invulnerable;
    private Component customName = Component.empty();
    private boolean customNameVisible;
    private boolean removed;

    public TestEntity(TestPlatform platform, TestWorld world) {
        super(platform);
        this.world = world;
    }

    public void move(Vector3dc position, float yaw, float pitch) {
        this.position.set(position);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean teleport(Vector3dc position, float yaw, float pitch) {
        move(position, yaw, pitch);
        return true;
    }

    public boolean isPersistent() {
        return persistent;
    }

    @Override
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    @Override
    public boolean isGlowing() {
        return glowing;
    }

    @Override
    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    @Override
    public boolean isInvulnerable() {
        return invulnerable;
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    @Override
    public Component getCustomName() {
        return customName;
    }

    @Override
    public void setCustomName(Component customName) {
        this.customName = customName;
    }

    @Override
    public boolean isCustomNameVisible() {
        return customNameVisible;
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        this.customNameVisible = visible;
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public TestWorld getWorld() {
        return world;
    }

    @Override
    public boolean isValid() {
        return !removed;
    }

    @Override
    public void remove() {
        removed = true;
    }
}
