package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.util.EasMath;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class EntityRotationProvider implements RotationProvider {
    private final Property<Location> property;
    private final Quaterniond rotation = new Quaterniond();

    public EntityRotationProvider(PropertyContainer properties) {
        this.property = properties.get(EntityPropertyTypes.LOCATION);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return EasMath.getEntityRotation(property.getValue(), rotation);
    }
}
