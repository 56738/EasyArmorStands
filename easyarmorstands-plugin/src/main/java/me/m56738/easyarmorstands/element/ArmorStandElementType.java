package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class ArmorStandElementType extends SimpleEntityElementType<ArmorStand> {
    public ArmorStandElementType() {
        super(EntityType.ARMOR_STAND, ArmorStand.class);
    }

    @Override
    protected SimpleEntityElement<ArmorStand> createInstance(ArmorStand entity) {
        return new ArmorStandElement(entity, this);
    }

    @Override
    public void applyDefaultProperties(PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(ArmorStandPropertyTypes.GRAVITY, false);
    }
}
