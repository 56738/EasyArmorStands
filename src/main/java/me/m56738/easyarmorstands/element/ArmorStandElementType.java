package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class ArmorStandElementType extends SimpleEntityElementType<ArmorStand> {
    public ArmorStandElementType() {
        super(ArmorStand.class, EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class)
                .getName(EntityType.ARMOR_STAND));
    }

    @Override
    protected SimpleEntityElement<ArmorStand> createInstance(ArmorStand entity) {
        return new ArmorStandElement(entity, this);
    }

    @Override
    public void applyDefaultProperties(PropertyRegistry properties) {
        super.applyDefaultProperties(properties);
        properties.merge(Property.of(PropertyTypes.ARMOR_STAND_GRAVITY, false));
    }
}
