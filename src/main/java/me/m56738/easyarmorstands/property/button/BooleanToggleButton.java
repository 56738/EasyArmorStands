package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.item.ItemTemplate;

public class BooleanToggleButton extends ToggleButton<Boolean> {
    public BooleanToggleButton(Property<Boolean> property, PropertyContainer container, ItemTemplate item) {
        super(property, container, item);
    }

    @Override
    public Boolean getNextValue() {
        return !property.getValue();
    }

    @Override
    public Boolean getPreviousValue() {
        return getNextValue();
    }
}
