package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.registry.EntityTypeKeys;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ArmorStandElementType extends SimpleEntityElementType<ArmorStand> {
    public ArmorStandElementType(EasyArmorStandsCommon eas) {
        super(eas, eas.platform().getEntityType(EntityTypeKeys.ARMOR_STAND), ArmorStand.class);
    }

    @Override
    protected SimpleEntityElement<ArmorStand> createInstance(ArmorStand entity) {
        return new ArmorStandElement(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(ArmorStandPropertyTypes.GRAVITY, false);
        properties.put(ArmorStandPropertyTypes.BASE_PLATE, false);
        properties.put(ArmorStandPropertyTypes.ARMS, true);
    }

    @Override
    public boolean isSpawnedAtEyeHeight() {
        return false;
    }
}
