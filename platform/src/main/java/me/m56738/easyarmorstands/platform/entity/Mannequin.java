package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.profile.Profile;
import me.m56738.easyarmorstands.platform.util.MainHand;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

public interface Mannequin extends LivingEntity {
    MainHand getMainHand();

    void setMainHand(MainHand mainHand);

    Profile getProfile();

    void setProfile(Profile profile);

    boolean isImmovable();

    void setImmovable(boolean immovable);

    @Nullable Component getDescription();

    void setDescription(@Nullable Component description);

    Pose getPose();

    void setPose(Pose pose);

    boolean isCapeVisible();

    void setCapeVisible(boolean visible);

    boolean isJacketVisible();

    void setJacketVisible(boolean visible);

    boolean isLeftSleeveVisible();

    void setLeftSleeveVisible(boolean visible);

    boolean isRightSleeveVisible();

    void setRightSleeveVisible(boolean visible);

    boolean isLeftPantsVisible();

    void setLeftPantsVisible(boolean visible);

    boolean isRightPantsVisible();

    void setRightPantsVisible(boolean visible);

    boolean isHatVisible();

    void setHatVisible(boolean visible);
}
