package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.common.util.EasMath;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3fc;

public class DisplayOffsetProvider implements OffsetProvider {
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Quaterniond currentRotation = new Quaterniond();
    private final Vector3d currentOffset = new Vector3d();

    public DisplayOffsetProvider(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
    }

    @Override
    public Vector3dc getOffset() {
        Location location = locationProperty.getValue();
        return currentOffset.set(translationProperty.getValue()).rotate(
                EasMath.getEntityRotation(location.yaw(), location.pitch(), currentRotation));
    }
}
