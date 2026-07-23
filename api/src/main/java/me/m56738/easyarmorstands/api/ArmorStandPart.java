package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.util.Rotations;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum ArmorStandPart {
    HEAD(
            ArmorStand::getHeadPose,
            ArmorStand::setHeadPose,
            Component.translatable("easyarmorstands.armor-stand-part.head")
    ),
    BODY(
            ArmorStand::getBodyPose,
            ArmorStand::setBodyPose,
            Component.translatable("easyarmorstands.armor-stand-part.body")
    ),
    LEFT_ARM(
            ArmorStand::getLeftArmPose,
            ArmorStand::setLeftArmPose,
            Component.translatable("easyarmorstands.armor-stand-part.left-arm")
    ),
    RIGHT_ARM(
            ArmorStand::getRightArmPose,
            ArmorStand::setRightArmPose,
            Component.translatable("easyarmorstands.armor-stand-part.right-arm")
    ),
    LEFT_LEG(
            ArmorStand::getLeftLegPose,
            ArmorStand::setLeftLegPose,
            Component.translatable("easyarmorstands.armor-stand-part.left-leg")
    ),
    RIGHT_LEG(
            ArmorStand::getRightLegPose,
            ArmorStand::setRightLegPose,
            Component.translatable("easyarmorstands.armor-stand-part.right-leg")
    );

    private final Function<ArmorStand, Rotations> poseGetter;
    private final BiConsumer<ArmorStand, Rotations> poseSetter;
    private final Component displayName;

    ArmorStandPart(Function<ArmorStand, Rotations> poseGetter, BiConsumer<ArmorStand, Rotations> poseSetter, Component displayName) {
        this.poseGetter = poseGetter;
        this.poseSetter = poseSetter;
        this.displayName = displayName;
    }

    public @NotNull Rotations getPose(@NotNull ArmorStand armorStand) {
        return poseGetter.apply(armorStand);
    }

    public void setPose(@NotNull ArmorStand armorStand, @NotNull Rotations pose) {
        poseSetter.accept(armorStand, pose);
    }

    public Component displayName() {
        return displayName;
    }
}
