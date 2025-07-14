package me.m56738.easyarmorstands.paper.property.display.interaction;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Interaction;

public class InteractionWidthProperty implements Property<Float> {
    private final Interaction entity;

    public InteractionWidthProperty(Interaction entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_WIDTH;
    }

    @Override
    public Float getValue() {
        return entity.getInteractionWidth();
    }

    @Override
    public boolean setValue(Float value) {
        entity.setInteractionWidth(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
