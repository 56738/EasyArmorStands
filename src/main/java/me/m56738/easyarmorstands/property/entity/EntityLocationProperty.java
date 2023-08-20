package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityLocationProperty implements Property<Location> {
    private final Entity entity;

    public EntityLocationProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Location> getType() {
        return PropertyTypes.ENTITY_LOCATION;
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
