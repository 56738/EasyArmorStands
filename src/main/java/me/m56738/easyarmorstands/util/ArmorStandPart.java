package me.m56738.easyarmorstands.util;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum ArmorStandPart {
    HEAD(
            ArmorStand::getHeadPose,
            ArmorStand::setHeadPose,
            "head",
            Component.text("Head"),
            new Vector3d(0, 23, 0),
            new Vector3d(0, 7, 0)
    ),
    BODY(
            ArmorStand::getBodyPose,
            ArmorStand::setBodyPose,
            "body",
            Component.text("Body"),
            new Vector3d(0, 24, 0),
            new Vector3d(0, -12, 0)
    ),
    LEFT_ARM(
            ArmorStand::getLeftArmPose,
            ArmorStand::setLeftArmPose,
            "leftarm",
            Component.text("Left arm"),
            new Vector3d(5, 22, 0),
            new Vector3d(0, -10, 0)
    ),
    RIGHT_ARM(
            ArmorStand::getRightArmPose,
            ArmorStand::setRightArmPose,
            "rightarm",
            Component.text("Right arm"),
            new Vector3d(-5, 22, 0),
            new Vector3d(0, -10, 0)
    ),
    LEFT_LEG(
            ArmorStand::getLeftLegPose,
            ArmorStand::setLeftLegPose,
            "leftleg",
            Component.text("Left leg"),
            new Vector3d(1.9, 12, 0),
            new Vector3d(0, -11, 0)
    ),
    RIGHT_LEG(
            ArmorStand::getRightLegPose,
            ArmorStand::setRightLegPose,
            "rightleg",
            Component.text("Right leg"),
            new Vector3d(-1.9, 12, 0),
            new Vector3d(0, -11, 0)
    );

    private final Function<ArmorStand, EulerAngle> poseGetter;
    private final BiConsumer<ArmorStand, EulerAngle> poseSetter;
    private final String name;
    private final Component displayName;
    private final Vector3dc offset;
    private final Vector3dc length;
    private final Vector3dc smallOffset;
    private final Vector3dc smallLength;

    ArmorStandPart(Function<ArmorStand, EulerAngle> poseGetter, BiConsumer<ArmorStand, EulerAngle> poseSetter, String name, Component displayName, Vector3dc offset, Vector3dc length) {
        this.poseGetter = poseGetter;
        this.poseSetter = poseSetter;
        this.name = name;
        this.displayName = displayName;
        this.offset = offset.div(16, new Vector3d());
        this.length = length.div(16, new Vector3d());
        this.smallOffset = this.offset.div(2, new Vector3d());
        this.smallLength = this.length.div(2, new Vector3d());
    }

    public EulerAngle getPose(ArmorStand armorStand) {
        return poseGetter.apply(armorStand);
    }

    public void setPose(ArmorStand armorStand, EulerAngle pose) {
        poseSetter.accept(armorStand, pose);
    }

    public String getName() {
        return name;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Vector3dc getOffset(ArmorStand armorStand) {
        if (armorStand.isSmall()) {
            return smallOffset;
        }
        return offset;
    }

    public Vector3dc getLength(ArmorStand armorStand) {
        if (armorStand.isSmall()) {
            return smallLength;
        }
        return length;
    }
}
