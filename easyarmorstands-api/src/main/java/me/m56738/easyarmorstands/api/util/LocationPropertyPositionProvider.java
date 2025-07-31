package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import org.joml.Vector3dc;
import org.jspecify.annotations.NullMarked;

@NullMarked
record LocationPropertyPositionProvider(Property<Location> property) implements PositionProvider {
    @Override
    public Vector3dc position() {
        return property.getValue().position();
    }
}
