package me.m56738.easyarmorstands.paper.property.display.interaction;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Interaction;

public class InteractionHeightProperty implements Property<Float> {
    private final Interaction entity;

    public InteractionHeightProperty(Interaction entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_HEIGHT;
    }

    @Override
    public Float getValue() {
        return entity.getInteractionHeight();
    }

    @Override
    public boolean setValue(Float value) {
        entity.setInteractionHeight(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
