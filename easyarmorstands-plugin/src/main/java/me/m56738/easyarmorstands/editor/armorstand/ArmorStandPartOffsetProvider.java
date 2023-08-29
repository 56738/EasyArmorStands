package me.m56738.easyarmorstands.editor.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import org.bukkit.Location;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartOffsetProvider implements OffsetProvider {
    private final ArmorStandPartInfo part;
    private final Property<Location> locationProperty;
    private final Property<ArmorStandSize> sizeProperty;
    private final Vector3d currentAnchor = new Vector3d();

    public ArmorStandPartOffsetProvider(PropertyContainer properties, ArmorStandPart part) {
        this.part = ArmorStandPartInfo.of(part);
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.sizeProperty = properties.get(ArmorStandPropertyTypes.SIZE);
    }

    @Override
    public Vector3dc getOffset() {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        return part.getOffset(size).rotateY(-Math.toRadians(location.getYaw()), currentAnchor);
    }
}
