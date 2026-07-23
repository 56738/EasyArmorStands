package me.m56738.easyarmorstands.editor.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.util.EasMath;
import org.joml.Quaterniond;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3fc;

public class BlockDisplayOffsetProvider implements OffsetProvider {
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Quaternionfc> leftRotationProperty;
    private final Property<Vector3fc> scaleProperty;
    private final Property<Quaternionfc> rightRotationProperty;
    private final Quaterniond currentRotation = new Quaterniond();
    private final Vector3d currentOffset = new Vector3d();

    public BlockDisplayOffsetProvider(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        this.leftRotationProperty = properties.get(DisplayPropertyTypes.LEFT_ROTATION);
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
        this.rightRotationProperty = properties.get(DisplayPropertyTypes.RIGHT_ROTATION);
    }

    @Override
    public Vector3dc getOffset() {
        Location location = locationProperty.getValue();
        return currentOffset.set(0.5)
                .rotate(new Quaterniond(rightRotationProperty.getValue()))
                .mul(scaleProperty.getValue())
                .rotate(new Quaterniond(leftRotationProperty.getValue()))
                .add(translationProperty.getValue())
                .rotate(EasMath.getEntityRotation(location.yaw(), location.pitch(), currentRotation));
    }
}
