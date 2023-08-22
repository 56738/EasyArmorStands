package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPoseProperty implements Property<EulerAngle> {
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final PropertyType<EulerAngle> type;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        this.entity = entity;
        this.part = part;
        this.type = ArmorStandPropertyTypes.POSE.get(part);
    }

    @Override
    public @NotNull PropertyType<EulerAngle> getType() {
        return type;
    }

    @Override
    public EulerAngle getValue() {
        return part.getPose(entity);
    }

    @Override
    public boolean setValue(EulerAngle value) {
        part.setPose(entity, value);
        return true;
    }
}
