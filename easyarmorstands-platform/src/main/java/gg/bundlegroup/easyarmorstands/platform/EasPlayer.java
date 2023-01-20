package gg.bundlegroup.easyarmorstands.platform;

import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3dc;
import org.joml.Vector3dc;

public interface EasPlayer extends EasArmorEntity, EasCommandSender {
    Vector3dc getEyePosition();

    Matrix3dc getEyeRotation();

    void hideEntity(EasEntity entity);

    void showEntity(EasEntity entity);

    boolean isSneaking();

    void giveTool();

    void lookAt(Vector3dc target);

    void showPoint(Vector3dc point, RGBLike color);

    void showLine(Vector3dc from, Vector3dc to, RGBLike color, boolean includeEnds);

    void openInventory(EasInventory inventory);
}
