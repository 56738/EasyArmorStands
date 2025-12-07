package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Mannequin;

public class MannequinImmovableProperty extends EntityProperty<Mannequin, Boolean> {
    public MannequinImmovableProperty(Mannequin entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return MannequinPropertyTypes.IMMOVABLE;
    }

    @Override
    public Boolean getValue() {
        return entity.isImmovable();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setImmovable(value);
        return true;
    }
}
