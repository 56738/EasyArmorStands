package me.m56738.easyarmorstands.common.editor.armorstand;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.util.EasMath;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class ArmorStandRotationProvider implements RotationProvider {
    private final Property<Location> locationProperty;

    public ArmorStandRotationProvider(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return EasMath.getEntityYawRotation(locationProperty.getValue().yaw(), new Quaterniond());
    }
}
