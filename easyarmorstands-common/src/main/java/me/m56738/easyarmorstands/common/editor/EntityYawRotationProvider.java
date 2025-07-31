package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.common.util.EasMath;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class EntityYawRotationProvider implements RotationProvider {
    private final Property<Location> property;
    private final Quaterniond rotation = new Quaterniond();

    public EntityYawRotationProvider(PropertyContainer properties) {
        this.property = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return EasMath.getEntityYawRotation(property.getValue().yaw(), rotation);
    }
}
