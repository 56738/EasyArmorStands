package me.m56738.easyarmorstands.display.editor;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplaySidePositionProvider implements PositionProvider {
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private final Vector3dc offset;

    public DisplaySidePositionProvider(PropertyContainer properties, Axis axis, boolean end) {
        this(properties, getOffset(axis, end));
    }

    public DisplaySidePositionProvider(PropertyContainer properties, Vector3dc offset) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.widthProperty = properties.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
        this.offset = new Vector3d(offset);
    }

    private static Vector3dc getOffset(Axis axis, boolean end) {
        Vector3d offset = new Vector3d(axis.getDirection());
        if (end) {
            // positive end
            offset.mul(0.5);
        } else {
            // negative end
            offset.mul(-0.5);
        }
        if (axis == Axis.Y) {
            offset.y += 0.5;
        }
        return offset;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        float width = widthProperty.getValue();
        float height = heightProperty.getValue();
        return Util.toVector3d(locationProperty.getValue())
                .add(offset.x() * width, offset.y() * height, offset.z() * width);
    }
}
