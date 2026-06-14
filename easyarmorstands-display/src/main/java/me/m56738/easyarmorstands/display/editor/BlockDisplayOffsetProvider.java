package me.m56738.easyarmorstands.display.editor;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Quaternionfc;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.joml.Vector3fc;
import me.m56738.easyarmorstands.util.EasMath;
import org.bukkit.Location;

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
                .rotate(EasMath.getEntityRotation(location.getYaw(), location.getPitch(), currentRotation));
    }
}
