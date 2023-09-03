package me.m56738.easyarmorstands.display.editor;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.util.EasMath;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionfc;

public class DisplayRotationProvider implements RotationProvider {
    private final Property<Location> locationProperty;
    private final Property<Quaternionfc> leftRotationProperty;

    public DisplayRotationProvider(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.leftRotationProperty = properties.get(DisplayPropertyTypes.LEFT_ROTATION);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        Location location = locationProperty.getValue();
        return EasMath.getEntityRotation(location.getYaw(), location.getPitch(), new Quaterniond())
                .mul(new Quaterniond(leftRotationProperty.getValue()));
    }
}
