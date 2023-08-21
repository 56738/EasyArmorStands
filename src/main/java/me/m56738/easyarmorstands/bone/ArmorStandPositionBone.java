package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPositionBone extends EntityLocationBone {
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPositionBone(PropertyContainer container) {
        super(container);
        this.sizeProperty = container.get(PropertyTypes.ARMOR_STAND_SIZE);
    }

    @Override
    public Vector3dc getOffset() {
        double offset = 1.25;
        if (sizeProperty.getValue().isSmall()) {
            offset /= 2;
        }
        return new Vector3d(0, offset, 0);
    }
}
