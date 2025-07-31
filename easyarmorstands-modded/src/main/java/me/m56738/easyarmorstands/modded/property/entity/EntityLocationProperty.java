package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import net.minecraft.world.entity.Entity;

public class EntityLocationProperty extends EntityProperty<Entity, Location> {
    public EntityLocationProperty(Entity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Location> getType() {
        return EntityPropertyTypes.LOCATION;
    }

    @Override
    public Location getValue() {
        return ModdedEntity.fromNative(entity).getLocation();
    }

    @Override
    public boolean setValue(Location value) {
        ModdedEntity.fromNative(entity).setLocation(value);
        return true;
    }
}
