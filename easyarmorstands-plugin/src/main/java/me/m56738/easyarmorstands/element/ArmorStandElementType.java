package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class ArmorStandElementType extends SimpleEntityElementType<ArmorStand> {
    public ArmorStandElementType() {
        super(EntityType.ARMOR_STAND, ArmorStand.class);
    }

    @Override
    protected SimpleEntityElement<ArmorStand> createInstance(ArmorStand entity) {
        return new ArmorStandElement(entity, this);
    }

    @Override
    public void applyDefaultProperties(@NotNull PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(ArmorStandPropertyTypes.GRAVITY, false);
        properties.put(ArmorStandPropertyTypes.BASE_PLATE, false);
        properties.put(ArmorStandPropertyTypes.ARMS, true);
    }
}
