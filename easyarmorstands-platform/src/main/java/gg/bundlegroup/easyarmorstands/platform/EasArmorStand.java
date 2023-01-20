package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface EasArmorStand extends EasEntity {
    boolean isVisible();

    void setVisible(boolean visible);

    boolean hasBasePlate();

    void setBasePlate(boolean basePlate);

    boolean hasArms();

    void setArms(boolean arms);

    boolean hasGravity();

    void setGravity(boolean gravity);

    boolean isSmall();

    void setSmall(boolean small);

    float getYaw();

    Vector3d getPose(Part part, Vector3d dest);

    void setPose(Part part, Vector3dc pose);

    EasItem getItem(Slot slot);

    void setItem(Slot slot, EasItem item);

    enum Part {
        HEAD,
        BODY,
        LEFT_ARM,
        RIGHT_ARM,
        LEFT_LEG,
        RIGHT_LEG
    }

    enum Slot {
        HEAD,
        BODY,
        LEFT_HAND,
        RIGHT_HAND,
        LEGS,
        FEET
    }
}
