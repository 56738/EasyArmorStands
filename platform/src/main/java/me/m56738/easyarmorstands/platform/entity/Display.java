package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

public interface Display extends Entity {
    float getDisplayWidth();

    void setDisplayWidth(float width);

    float getDisplayHeight();

    void setDisplayHeight(float height);

    Vector3fc getTranslation();

    void setTranslation(Vector3fc translation);

    Quaternionfc getLeftRotation();

    void setLeftRotation(Quaternionfc rotation);

    Vector3fc getScale();

    void setScale(Vector3fc scale);

    Quaternionfc getRightRotation();

    void setRightRotation(Quaternionfc rotation);

    Billboard getBillboard();

    void setBillboard(Billboard billboard);

    @Nullable Brightness getBrightness();

    void setBrightness(@Nullable Brightness brightness);

    @Nullable RGBColor getGlowColorOverride();

    void setGlowColorOverride(@Nullable RGBColor glowColor);

    float getViewRange();

    void setViewRange(float range);

    enum Billboard {
        FIXED,
        VERTICAL,
        HORIZONTAL,
        CENTER,
    }

    record Brightness(int blockLight, int skyLight) {
    }
}
