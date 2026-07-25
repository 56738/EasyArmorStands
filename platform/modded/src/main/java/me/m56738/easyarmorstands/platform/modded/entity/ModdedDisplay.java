package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.modded.ModdedAdapter;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

public interface ModdedDisplay extends Display, ModdedEntity {
    @Override
    net.minecraft.world.entity.Display getNative();

    static ModdedDisplay fromNative(ModdedPlatform platform, net.minecraft.world.entity.Display entity) {
        return switch (entity) {
            case net.minecraft.world.entity.Display.BlockDisplay e -> ModdedBlockDisplay.fromNative(platform, e);
            case net.minecraft.world.entity.Display.ItemDisplay e -> ModdedItemDisplay.fromNative(platform, e);
            case net.minecraft.world.entity.Display.TextDisplay e -> ModdedTextDisplay.fromNative(platform, e);
            default -> new ModdedDisplayImpl(platform, entity);
        };
    }

    static net.minecraft.world.entity.Display toNative(Display entity) {
        return ((ModdedDisplay) entity).getNative();
    }

    @Override
    default float getDisplayWidth() {
        return getNative().getWidth();
    }

    @Override
    default void setDisplayWidth(float width) {
        getNative().setWidth(width);
    }

    @Override
    default float getDisplayHeight() {
        return getNative().getHeight();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    default void setDisplayHeight(float height) {
        getNative().setHeight(height);
    }

    @Override
    default Vector3fc getTranslation() {
        return getNative().getEntityData().get(net.minecraft.world.entity.Display.DATA_TRANSLATION_ID);
    }

    @Override
    default void setTranslation(Vector3fc translation) {
        getNative().getEntityData().set(net.minecraft.world.entity.Display.DATA_TRANSLATION_ID, translation);
    }

    @Override
    default Quaternionfc getLeftRotation() {
        return getNative().getEntityData().get(net.minecraft.world.entity.Display.DATA_LEFT_ROTATION_ID);
    }

    @Override
    default void setLeftRotation(Quaternionfc rotation) {
        getNative().getEntityData().set(net.minecraft.world.entity.Display.DATA_LEFT_ROTATION_ID, rotation);
    }

    @Override
    default Vector3fc getScale() {
        return getNative().getEntityData().get(net.minecraft.world.entity.Display.DATA_SCALE_ID);
    }

    @Override
    default void setScale(Vector3fc scale) {
        getNative().getEntityData().set(net.minecraft.world.entity.Display.DATA_SCALE_ID, scale);
    }

    @Override
    default Quaternionfc getRightRotation() {
        return getNative().getEntityData().get(net.minecraft.world.entity.Display.DATA_RIGHT_ROTATION_ID);
    }

    @Override
    default void setRightRotation(Quaternionfc rotation) {
        getNative().getEntityData().set(net.minecraft.world.entity.Display.DATA_RIGHT_ROTATION_ID, rotation);
    }

    @Override
    default Billboard getBillboard() {
        return ModdedAdapter.fromNative(getNative().getBillboardConstraints());
    }

    @Override
    default void setBillboard(Billboard billboard) {
        getNative().setBillboardConstraints(ModdedAdapter.toNative(billboard));
    }

    @Override
    default @Nullable Brightness getBrightness() {
        net.minecraft.util.Brightness brightness = getNative().getBrightnessOverride();
        if (brightness == null) {
            return null;
        }
        return ModdedAdapter.fromNative(brightness);
    }

    @Override
    default void setBrightness(@Nullable Brightness brightness) {
        getNative().setBrightnessOverride(brightness != null ? ModdedAdapter.toNative(brightness) : null);
    }

    @Override
    default @Nullable RGBColor getGlowColorOverride() {
        int value = getNative().getGlowColorOverride();
        if (value == -1) {
            return null;
        }
        return RGBColor.of(value);
    }

    @Override
    default void setGlowColorOverride(@Nullable RGBColor glowColor) {
        getNative().setGlowColorOverride(glowColor != null ? glowColor.value() : -1);
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
