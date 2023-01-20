package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Vector3dc;

public interface EasEntity extends EasWrapper {
    void update();

    void teleport(Vector3dc position, float yaw, float pitch);

    void setPersistent(boolean persistent);

    boolean isGlowing();

    void setGlowing(boolean glowing);

    Vector3dc getPosition();

    EasWorld getWorld();

    boolean isValid();

    void remove();
}
