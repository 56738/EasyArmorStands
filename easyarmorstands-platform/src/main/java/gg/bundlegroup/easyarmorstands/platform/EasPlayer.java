package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Matrix3dc;
import org.joml.Vector3dc;

public interface EasPlayer extends EasEntity, EasCommandSender {
    Vector3dc getEyePosition();

    Matrix3dc getEyeRotation();

    void hideEntity(EasEntity entity);

    void showEntity(EasEntity entity);

    void giveTool();
}
