package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import org.jetbrains.annotations.NotNull;

public class DisplayElementType extends SimpleEntityElementType {
    private final EasyArmorStandsCommon eas;

    public DisplayElementType(EasyArmorStandsCommon eas, EntityType entityType) {
        super(eas, entityType);
        this.eas = eas;
    }

    @Override
    protected SimpleEntityElement createInstance(Entity entity) {
        return new DisplayElement(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        locationProperty.setValue(locationProperty.getValue().withRotation(0, 0));
    }
}
