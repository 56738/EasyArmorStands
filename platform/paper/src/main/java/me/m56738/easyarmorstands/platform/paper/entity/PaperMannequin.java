package me.m56738.easyarmorstands.platform.paper.entity;

import com.destroystokyo.paper.SkinParts;
import me.m56738.easyarmorstands.platform.entity.Mannequin;
import me.m56738.easyarmorstands.platform.entity.Pose;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.paper.profile.PaperProfile;
import me.m56738.easyarmorstands.platform.profile.Profile;
import me.m56738.easyarmorstands.platform.util.MainHand;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

public interface PaperMannequin extends Mannequin, PaperLivingEntity {
    static PaperMannequin fromNative(org.bukkit.entity.Mannequin entity) {
        return new PaperMannequinImpl(entity);
    }

    org.bukkit.entity.Mannequin getNative();

    static org.bukkit.entity.Mannequin toNative(Mannequin entity) {
        return ((PaperMannequin) entity).getNative();
    }

    @Override
    default MainHand getMainHand() {
        return PaperAdapter.fromNative(getNative().getMainHand());
    }

    @Override
    default void setMainHand(MainHand mainHand) {
        getNative().setMainHand(PaperAdapter.toNative(mainHand));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default Profile getProfile() {
        return PaperProfile.fromNative(getNative().getProfile());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default void setProfile(Profile profile) {
        getNative().setProfile(PaperProfile.toNative(profile));
    }

    @Override
    default boolean isImmovable() {
        return getNative().isImmovable();
    }

    @Override
    default void setImmovable(boolean immovable) {
        getNative().setImmovable(immovable);
    }

    @Override
    default @Nullable Component getDescription() {
        return getNative().getDescription();
    }

    @Override
    default void setDescription(@Nullable Component description) {
        getNative().setDescription(description);
    }

    @Override
    default Pose getPose() {
        return PaperPose.fromNative(getNative().getPose());
    }

    @Override
    default void setPose(Pose pose) {
        getNative().setPose(PaperPose.toNative(pose));
    }

    @Override
    default boolean isCapeVisible() {
        return getNative().getSkinParts().hasCapeEnabled();
    }

    @Override
    default void setCapeVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setCapeEnabled(visible);
        getNative().setSkinParts(parts);
    }

    @Override
    default boolean isJacketVisible() {
        return getNative().getSkinParts().hasJacketEnabled();
    }

    @Override
    default void setJacketVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setJacketEnabled(visible);
        getNative().setSkinParts(parts);
    }

    @Override
    default boolean isLeftSleeveVisible() {
        return getNative().getSkinParts().hasLeftSleeveEnabled();
    }

    @Override
    default void setLeftSleeveVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setLeftSleeveEnabled(visible);
        getNative().setSkinParts(parts);
    }

    @Override
    default boolean isRightSleeveVisible() {
        return getNative().getSkinParts().hasRightSleeveEnabled();
    }

    @Override
    default void setRightSleeveVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setRightSleeveEnabled(visible);
        getNative().setSkinParts(parts);
    }

    @Override
    default boolean isLeftPantsVisible() {
        return getNative().getSkinParts().hasLeftPantsEnabled();
    }

    @Override
    default void setLeftPantsVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setLeftPantsEnabled(visible);
        getNative().setSkinParts(parts);
    }

    @Override
    default boolean isRightPantsVisible() {
        return getNative().getSkinParts().hasRightPantsEnabled();
    }

    @Override
    default void setRightPantsVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setRightPantsEnabled(visible);
        getNative().setSkinParts(parts);
    }

    @Override
    default boolean isHatVisible() {
        return getNative().getSkinParts().hasHatsEnabled();
    }

    @Override
    default void setHatVisible(boolean visible) {
        SkinParts.Mutable parts = getNative().getSkinParts();
        parts.setHatsEnabled(visible);
        getNative().setSkinParts(parts);
    }
}
