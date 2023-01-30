package me.m56738.easyarmorstands.core.platform;

import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface EasArmorStand extends EasArmorEntity {
    boolean isVisible();

    void setVisible(boolean visible);

    boolean isLocked();

    void setLocked(boolean locked);

    boolean hasBasePlate();

    void setBasePlate(boolean basePlate);

    boolean hasArms();

    void setArms(boolean arms);

    boolean hasGravity();

    void setGravity(boolean gravity);

    boolean isSmall();

    void setSmall(boolean small);

    boolean canTick();

    void setCanTick(boolean canTick);

    Vector3d getPose(Part part, Vector3d dest);

    void setPose(Part part, Vector3dc pose);

    enum Part {
        HEAD,
        BODY,
        LEFT_ARM,
        RIGHT_ARM,
        LEFT_LEG,
        RIGHT_LEG
    }
}
