package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.util.Rotations;
import org.jetbrains.annotations.NotNull;

public class ArmorStandPoseProperty implements Property<Rotations> {
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final PropertyType<Rotations> type;

    public ArmorStandPoseProperty(ArmorStand entity, ArmorStandPart part) {
        this.entity = entity;
        this.part = part;
        this.type = ArmorStandPropertyTypes.POSE.get(part);
    }

    @Override
    public @NotNull PropertyType<Rotations> getType() {
        return type;
    }

    @Override
    public @NotNull Rotations getValue() {
        return part.getPose(entity);
    }

    @Override
    public boolean setValue(@NotNull Rotations value) {
        part.setPose(entity, value);
        return true;
    }
}
