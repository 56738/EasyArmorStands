package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class EntityPositionProvider implements PositionProvider {
    private final Property<Location> property;
    private final OffsetProvider offsetProvider;
    private final Vector3d position = new Vector3d();

    public EntityPositionProvider(PropertyContainer properties, OffsetProvider offsetProvider) {
        this.property = properties.get(EntityPropertyTypes.LOCATION);
        this.offsetProvider = offsetProvider;
    }

    public EntityPositionProvider(PropertyContainer properties) {
        this(properties, OffsetProvider.zero());
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return property.getValue().position().add(offsetProvider.getOffset(), position);
    }
}
