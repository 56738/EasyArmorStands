package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.util.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class EntityLocationProperty implements Property<Location> {
    private static final double HEIGHT_LIMIT = 20000000;
    private static final double COORDINATE_LIMIT = 30000000;
    private final Entity entity;

    public EntityLocationProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Location> getType() {
        return EntityPropertyTypes.LOCATION;
    }

    @Override
    public @NotNull Location getValue() {
        return entity.location();
    }

    @Override
    public @Nullable PendingChange prepareChange(@NotNull Location value) {
        if (!isValid(value)) {
            return null;
        }

        return Property.super.prepareChange(value);
    }

    @Override
    public boolean setValue(@NotNull Location value) {
        if (!isValid(value)) {
            return false;
        }

        boolean ok = entity.teleport(value);
        if (ok) {
            entity.setFallDistance(0);
        }
        return ok;
    }

    private boolean isValid(@NotNull Location location) {
        Vector3dc position = location.position();
        return isValid(position.x(), COORDINATE_LIMIT) &&
                isValid(position.y(), HEIGHT_LIMIT) &&
                isValid(position.z(), COORDINATE_LIMIT);
    }

    private boolean isValid(double value, double limit) {
        return isValid(value, -limit, limit);
    }

    private boolean isValid(double value, double min, double max) {
        return value >= min && value < max;
    }
}
