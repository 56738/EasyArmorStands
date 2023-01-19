package gg.bundlegroup.easyarmorstands.platform.test;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class TestEntity extends TestWrapper implements EasEntity {
    protected final Vector3d nextPosition = new Vector3d();
    private final TestWorld world;
    private final Vector3d position = new Vector3d();
    private boolean moved = true;
    private boolean persistent = true;
    private boolean glowing;
    private boolean removed;

    public TestEntity(TestPlatform platform, TestWorld world) {
        super(platform);
        this.world = world;
    }

    protected void assertUpToDate() {
        if (moved) {
            throw new IllegalArgumentException("Attempted to access an outdated property");
        }
    }

    public void move(Vector3dc position, float yaw, float pitch) {
        moved = true;
        nextPosition.set(position);
    }

    @Override
    public void update() {
        moved = false;
        position.set(nextPosition);
    }

    @Override
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    @Override
    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    @Override
    public Vector3dc getPosition() {
        assertUpToDate();
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
