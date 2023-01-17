package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Vector3dc;

public interface EasEntity extends EasWrapper {
    void update();

    void setPersistent(boolean persistent);

    void setGlowing(boolean glowing);

    Vector3dc getPosition();

    EasWorld getWorld();

    boolean isValid();

    void remove();
}
