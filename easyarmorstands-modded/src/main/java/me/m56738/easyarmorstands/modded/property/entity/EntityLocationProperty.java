package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import net.minecraft.world.entity.Entity;

public class EntityLocationProperty extends EntityProperty<Entity, Location> {
    private final ModdedPlatform platform;

    public EntityLocationProperty(ModdedPlatform platform, Entity entity) {
        super(entity);
        this.platform = platform;
    }

    @Override
    public PropertyType<Location> getType() {
        return EntityPropertyTypes.LOCATION;
    }

    @Override
    public Location getValue() {
        return platform.getEntity(entity).getLocation();
    }

    @Override
    public boolean setValue(Location value) {
        platform.getEntity(entity).setLocation(value);
        return true;
    }
}
