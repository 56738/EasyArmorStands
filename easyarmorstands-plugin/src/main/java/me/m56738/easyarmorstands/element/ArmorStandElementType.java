package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class ArmorStandElementType extends SimpleEntityElementType<ArmorStand> {
    public ArmorStandElementType() {
        super(ArmorStand.class, EasyArmorStandsPlugin.getInstance().getCapability(EntityTypeCapability.class)
                .getName(EntityType.ARMOR_STAND));
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
