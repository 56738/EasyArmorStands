package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.registry.EntityTypeKeys;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class DisplayElementType<E extends Display> extends SimpleEntityElementType<E> {
    public DisplayElementType(EasyArmorStandsCommon eas, EntityType entityType, Class<E> entityClass) {
        super(eas, entityType, entityClass);
    }

    @Override
    protected SimpleEntityElement<E> createInstance(E entity) {
        return new DisplayElement<>(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        Property<Location> locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        Location location = locationProperty.getValue().withYaw(0).withPitch(0);
        locationProperty.setValue(location);
        if (getEntityType().key().equals(EntityTypeKeys.BLOCK_DISPLAY) && eas.getConfiguration().editor.centeredPivot) {
            properties.put(DisplayPropertyTypes.TRANSLATION, new Vector3f(-0.5f));
        }
    }
}
