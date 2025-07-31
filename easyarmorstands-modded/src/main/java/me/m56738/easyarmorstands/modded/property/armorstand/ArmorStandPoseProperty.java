package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.core.Rotations;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandPoseProperty extends EntityProperty<ArmorStand, EulerAngles> {
    private final ArmorStandPart part;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        super(entity);
        this.part = part;
    }

    @Override
    public PropertyType<EulerAngles> getType() {
        return ArmorStandPropertyTypes.POSE.get(part);
    }

    @Override
    public EulerAngles getValue() {
        Rotations rotations = switch (part) {
            case HEAD -> entity.getHeadPose();
            case BODY -> entity.getBodyPose();
            case LEFT_ARM -> entity.getLeftArmPose();
            case RIGHT_ARM -> entity.getRightArmPose();
            case LEFT_LEG -> entity.getLeftLegPose();
            case RIGHT_LEG -> entity.getRightLegPose();
        };
        return EulerAngles.of(
                Math.toRadians(rotations.x()),
                Math.toRadians(rotations.y()),
                Math.toRadians(rotations.z()));
    }

    @Override
    public boolean setValue(EulerAngles value) {
        Rotations rotations = new Rotations(
                (float) Math.toDegrees(value.x()),
                (float) Math.toDegrees(value.y()),
                (float) Math.toDegrees(value.z()));
        switch (part) {
            case HEAD -> entity.setHeadPose(rotations);
            case BODY -> entity.setBodyPose(rotations);
            case LEFT_ARM -> entity.setLeftArmPose(rotations);
            case RIGHT_ARM -> entity.setRightArmPose(rotations);
            case LEFT_LEG -> entity.setLeftLegPose(rotations);
            case RIGHT_LEG -> entity.setRightLegPose(rotations);
        }
        return true;
    }
}
