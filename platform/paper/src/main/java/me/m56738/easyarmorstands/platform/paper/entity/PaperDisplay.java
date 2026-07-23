package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import org.bukkit.Color;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

public interface PaperDisplay extends Display, PaperEntity {
    static PaperDisplay fromNative(org.bukkit.entity.Display entity) {
        return switch (entity) {
            case BlockDisplay e -> PaperBlockDisplay.fromNative(e);
            case ItemDisplay e -> PaperItemDisplay.fromNative(e);
            case TextDisplay e -> PaperTextDisplay.fromNative(e);
            default -> new PaperDisplayImpl(entity);
        };
    }

    org.bukkit.entity.Display getNative();

    static org.bukkit.entity.Display toNative(Display entity) {
        return ((PaperDisplay) entity).getNative();
    }

    @Override
    default float getDisplayWidth() {
        return getNative().getDisplayWidth();
    }

    @Override
    default void setDisplayWidth(float width) {
        getNative().setDisplayWidth(width);
    }

    @Override
    default float getDisplayHeight() {
        return getNative().getDisplayHeight();
    }

    @Override
    default void setDisplayHeight(float height) {
        getNative().setDisplayHeight(height);
    }

    @Override
    default Vector3fc getTranslation() {
        return getNative().getTransformation().getTranslation();
    }

    @Override
    default void setTranslation(Vector3fc translation) {
        Transformation transformation = getNative().getTransformation();
        transformation.getTranslation().set(translation);
        getNative().setTransformation(transformation);
    }

    @Override
    default Quaternionfc getLeftRotation() {
        return getNative().getTransformation().getLeftRotation();
    }

    @Override
    default void setLeftRotation(Quaternionfc rotation) {
        Transformation transformation = getNative().getTransformation();
        transformation.getLeftRotation().set(rotation);
        getNative().setTransformation(transformation);
    }

    @Override
    default Vector3fc getScale() {
        return getNative().getTransformation().getScale();
    }

    @Override
    default void setScale(Vector3fc scale) {
        Transformation transformation = getNative().getTransformation();
        transformation.getScale().set(scale);
        getNative().setTransformation(transformation);
    }

    @Override
    default Quaternionfc getRightRotation() {
        return getNative().getTransformation().getRightRotation();
    }

    @Override
    default void setRightRotation(Quaternionfc rotation) {
        Transformation transformation = getNative().getTransformation();
        transformation.getRightRotation().set(rotation);
        getNative().setTransformation(transformation);
    }

    @Override
    default Billboard getBillboard() {
        return PaperAdapter.fromNative(getNative().getBillboard());
    }

    @Override
    default void setBillboard(Billboard billboard) {
        getNative().setBillboard(PaperAdapter.toNative(billboard));
    }

    @Override
    default @Nullable Brightness getBrightness() {
        org.bukkit.entity.Display.Brightness brightness = getNative().getBrightness();
        if (brightness == null) {
            return null;
        }
        return PaperAdapter.fromNative(brightness);
    }

    @Override
    default void setBrightness(@Nullable Brightness brightness) {
        getNative().setBrightness(brightness != null ? PaperAdapter.toNative(brightness) : null);
    }

    @Override
    default @Nullable RGBColor getGlowColorOverride() {
        Color color = getNative().getGlowColorOverride();
        if (color == null) {
            return null;
        }
        return RGBColor.of(color.asRGB());
    }

    @Override
    default void setGlowColorOverride(@Nullable RGBColor glowColor) {
        getNative().setGlowColorOverride(glowColor != null ? Color.fromRGB(glowColor.value()) : null);
    }

    @Override
    default float getViewRange() {
        return getNative().getViewRange();
    }

    @Override
    default void setViewRange(float range) {
        getNative().setViewRange(range);
    }
}
