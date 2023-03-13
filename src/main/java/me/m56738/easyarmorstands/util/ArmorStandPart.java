package me.m56738.easyarmorstands.util;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum ArmorStandPart {
    HEAD(ArmorStand::getHeadPose, ArmorStand::setHeadPose, "head"),
    BODY(ArmorStand::getBodyPose, ArmorStand::setBodyPose, "body"),
    LEFT_ARM(ArmorStand::getLeftArmPose, ArmorStand::setLeftArmPose, "left arm"),
    RIGHT_ARM(ArmorStand::getRightArmPose, ArmorStand::setRightArmPose, "right arm"),
    LEFT_LEG(ArmorStand::getLeftLegPose, ArmorStand::setLeftLegPose, "left leg"),
    RIGHT_LEG(ArmorStand::getRightLegPose, ArmorStand::setRightLegPose, "right leg");

    private final Function<ArmorStand, EulerAngle> poseGetter;
    private final BiConsumer<ArmorStand, EulerAngle> poseSetter;
    private final String name;

    ArmorStandPart(Function<ArmorStand, EulerAngle> poseGetter, BiConsumer<ArmorStand, EulerAngle> poseSetter, String name) {
        this.poseGetter = poseGetter;
        this.poseSetter = poseSetter;
        this.name = name;
    }

    public EulerAngle getPose(ArmorStand armorStand) {
        return poseGetter.apply(armorStand);
    }

    public void setPose(ArmorStand armorStand, EulerAngle pose) {
        poseSetter.accept(armorStand, pose);
    }

    @Override
    public String toString() {
        return name;
    }
}
