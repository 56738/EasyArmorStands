package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return entity.getLocation();
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private boolean isValid(@NotNull Location location) {
        return isValid(location.getX(), COORDINATE_LIMIT) &&
                isValid(location.getY(), HEIGHT_LIMIT) &&
                isValid(location.getZ(), COORDINATE_LIMIT);
    }

    private boolean isValid(double value, double limit) {
        return isValid(value, -limit, limit);
    }

    private boolean isValid(double value, double min, double max) {
        return value >= min && value < max;
    }
}
