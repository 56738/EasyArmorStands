package me.m56738.easyarmorstands.common.element;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import org.jetbrains.annotations.NotNull;

public class ArmorStandElementType extends SimpleEntityElementType {
    private final EasyArmorStandsCommon eas;

    public ArmorStandElementType(EasyArmorStandsCommon eas) {
        super(eas, eas.platform().getArmorStandType());
        this.eas = eas;
    }

    @Override
    protected SimpleEntityElement createInstance(Entity entity) {
        return new ArmorStandElement(eas, entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
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
