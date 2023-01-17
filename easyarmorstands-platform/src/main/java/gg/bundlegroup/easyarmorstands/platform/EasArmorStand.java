package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface EasArmorStand extends EasEntity {
    void setVisible(boolean visible);

    void setBasePlate(boolean basePlate);

    void setArms(boolean arms);

    void setGravity(boolean gravity);

    boolean isSmall();

    float getYaw();

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
