package me.m56738.easyarmorstands.paper.property.display.interaction;

import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Interaction;

public class InteractionResponsiveProperty extends EntityProperty<Interaction, Boolean> {
    public InteractionResponsiveProperty(Interaction entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return InteractionPropertyTypes.RESPONSIVE;
    }

    @Override
    public Boolean getValue() {
        return entity.isResponsive();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setResponsive(value);
        return true;
    }
}
