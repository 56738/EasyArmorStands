package me.m56738.easyarmorstands.paper.property.display.interaction;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Interaction;

public class InteractionHeightProperty extends EntityProperty<Interaction, Float> {
    public InteractionHeightProperty(Interaction entity) {
        super(entity);
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
}
