package me.m56738.easyarmorstands.display.editor;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class DisplayBoxPositionProvider implements PositionProvider {
    private final Property<Location> locationProperty;
    private final Property<Float> heightProperty;

    public DisplayBoxPositionProvider(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        float height = heightProperty.getValue();
        return Util.toVector3d(locationProperty.getValue()).add(0, height / 2, 0);
    }
}
