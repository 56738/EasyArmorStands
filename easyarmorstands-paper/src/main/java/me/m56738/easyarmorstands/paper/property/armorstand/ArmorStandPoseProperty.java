package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class ArmorStandPoseProperty implements Property<EulerAngles> {
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final PropertyType<EulerAngles> type;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        this.entity = entity;
        this.part = part;
        this.type = ArmorStandPropertyTypes.POSE.get(part);
    }

    @Override
    public PropertyType<EulerAngles> getType() {
        return type;
    }

    @Override
    public EulerAngles getValue() {
        EulerAngle pose = part.getPose(entity);
        return EulerAngles.of(pose.getX(), pose.getY(), pose.getZ());
    }

    @Override
    public boolean setValue(EulerAngles value) {
        part.setPose(entity, new EulerAngle(value.x(), value.y(), value.z()));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
