package gg.bundlegroup.easyarmorstands.platform;

import org.joml.Matrix3dc;
import org.joml.Vector3dc;

import java.awt.*;

public interface EasPlayer extends EasEntity, EasCommandSender {
    Vector3dc getEyePosition();

    Matrix3dc getEyeRotation();

    void hideEntity(EasEntity entity);

    void showEntity(EasEntity entity);

    void giveTool();

    void lookAt(Vector3dc target);

    void showPoint(Vector3dc point, Color color);

    void showLine(Vector3dc from, Vector3dc to, Color color, boolean includeEnds);
}
