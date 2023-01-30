package me.m56738.easyarmorstands.core.platform;

import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3dc;
import org.joml.Vector3dc;

public interface EasPlayer extends EasArmorEntity, EasCommandSender {
    Vector3dc getEyePosition();

    Matrix3dc getEyeRotation();

    void hideEntity(EasEntity entity);

    void showEntity(EasEntity entity);

    boolean isSneaking();

    boolean isFlying();

    void giveTool();

    void lookAt(Vector3dc target);

    void showPoint(Vector3dc point, RGBLike color);

    void showLine(Vector3dc from, Vector3dc to, RGBLike color, boolean includeEnds);

    void showCircle(Vector3dc center, Vector3dc axis, RGBLike color, double radius);

    void openInventory(EasInventory inventory);

    void closeInventory(EasInventory inventory);

    void setCursor(EasItem item);
}
