package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.platform.Platform;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.element.SimpleEntityElement;
import me.m56738.easyarmorstands.element.SimpleEntityElementType;
import org.jetbrains.annotations.NotNull;

public class DisplayElementType extends SimpleEntityElementType {
    private final Platform platform;

    public DisplayElementType(Platform platform, EntityType entityType) {
        super(platform, entityType);
        this.platform = platform;
    }

    @Override
    protected SimpleEntityElement createInstance(Entity entity) {
        return new DisplayElement(platform, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        locationProperty.setValue(locationProperty.getValue().withRotation(0, 0));
    }
}
