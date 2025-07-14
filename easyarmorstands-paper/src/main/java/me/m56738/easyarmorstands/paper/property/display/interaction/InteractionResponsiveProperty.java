package me.m56738.easyarmorstands.paper.property.display.interaction;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Interaction;

public class InteractionResponsiveProperty implements Property<Boolean> {
    private final Interaction entity;

    public InteractionResponsiveProperty(Interaction entity) {
        this.entity = entity;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
