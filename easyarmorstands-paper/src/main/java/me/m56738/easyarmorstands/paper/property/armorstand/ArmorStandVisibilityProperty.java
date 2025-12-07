package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;

public class ArmorStandVisibilityProperty extends EntityProperty<ArmorStand, Boolean> {
    public static final PropertyType<Boolean> TYPE = EntityPropertyTypes.VISIBLE;

    public ArmorStandVisibilityProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.isVisible();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setVisible(value);
        return true;
    }
}
