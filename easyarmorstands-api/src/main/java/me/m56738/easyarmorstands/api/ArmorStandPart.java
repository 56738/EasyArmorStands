package me.m56738.easyarmorstands.api;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum ArmorStandPart {
    HEAD(
            ArmorStand::getHeadPose,
            ArmorStand::setHeadPose
    ),
    BODY(
            ArmorStand::getBodyPose,
            ArmorStand::setBodyPose
    ),
    LEFT_ARM(
            ArmorStand::getLeftArmPose,
            ArmorStand::setLeftArmPose
    ),
    RIGHT_ARM(
            ArmorStand::getRightArmPose,
            ArmorStand::setRightArmPose
    ),
    LEFT_LEG(
            ArmorStand::getLeftLegPose,
            ArmorStand::setLeftLegPose
    ),
    RIGHT_LEG(
            ArmorStand::getRightLegPose,
            ArmorStand::setRightLegPose
    );

    private final Function<ArmorStand, EulerAngle> poseGetter;
    private final BiConsumer<ArmorStand, EulerAngle> poseSetter;

    ArmorStandPart(Function<ArmorStand, EulerAngle> poseGetter, BiConsumer<ArmorStand, EulerAngle> poseSetter) {
        this.poseGetter = poseGetter;
        this.poseSetter = poseSetter;
    }

    public EulerAngle getPose(ArmorStand armorStand) {
        return poseGetter.apply(armorStand);
    }

    public void setPose(ArmorStand armorStand, EulerAngle pose) {
        poseSetter.accept(armorStand, pose);
    }
}
