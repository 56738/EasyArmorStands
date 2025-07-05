package me.m56738.easyarmorstands.editor.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class ArmorStandPartRotationProvider implements RotationProvider {
    private final Property<Location> locationProperty;
    private final Property<EulerAngle> poseProperty;
    private final Quaterniond currentRotation = new Quaterniond();

    public ArmorStandPartRotationProvider(PropertyContainer properties, ArmorStandPart part) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.poseProperty = properties.get(ArmorStandPropertyTypes.POSE.get(part));
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        Location location = locationProperty.getValue();
        return Util.fromEuler(poseProperty.getValue(), currentRotation)
                .rotateLocalY(-Math.toRadians(location.getYaw()));
    }
}
