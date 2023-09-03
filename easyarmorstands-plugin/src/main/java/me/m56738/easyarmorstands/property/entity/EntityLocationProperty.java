package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityLocationProperty implements Property<Location> {
    private final Entity entity;

    public EntityLocationProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Location> getType() {
        return EntityPropertyTypes.LOCATION;
    }

    @Override
    public Location getValue() {
        return entity.getLocation();
    }

    @Override
    public boolean setValue(Location value) {
        return entity.teleport(value);
    }
}
