package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class ArmorStandPoseProperty extends EntityProperty<ArmorStand, EulerAngles> {
    private final ArmorStandPart part;
    private final PropertyType<EulerAngles> type;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        super(entity);
        this.part = part;
        this.type = ArmorStandPropertyTypes.POSE.get(part);
    }

    @Override
    public PropertyType<EulerAngles> getType() {
        return type;
    }

    @Override
    public EulerAngles getValue() {
        EulerAngle pose = switch (part) {
            case HEAD -> entity.getHeadPose();
            case BODY -> entity.getBodyPose();
            case LEFT_ARM -> entity.getLeftArmPose();
            case RIGHT_ARM -> entity.getRightArmPose();
            case LEFT_LEG -> entity.getLeftLegPose();
            case RIGHT_LEG -> entity.getRightLegPose();
        };
        return EulerAngles.of(pose.getX(), pose.getY(), pose.getZ());
    }

    @Override
    public boolean setValue(EulerAngles value) {
        EulerAngle pose = new EulerAngle(value.x(), value.y(), value.z());
        switch (part) {
            case HEAD -> entity.setHeadPose(pose);
            case BODY -> entity.setBodyPose(pose);
            case LEFT_ARM -> entity.setLeftArmPose(pose);
            case RIGHT_ARM -> entity.setRightArmPose(pose);
            case LEFT_LEG -> entity.setLeftLegPose(pose);
            case RIGHT_LEG -> entity.setRightLegPose(pose);
        }
        return true;
    }
}
