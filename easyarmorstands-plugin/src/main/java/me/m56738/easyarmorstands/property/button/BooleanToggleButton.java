package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;

public class BooleanToggleButton extends ToggleButton<Boolean> {
    public BooleanToggleButton(Element element, PropertyType<Boolean> type, SimpleItemTemplate item) {
        super(element, type, item);
    }

    @Override
    public Boolean getNextValue() {
        return !getUntrackedProperty().getValue();
    }

    @Override
    public Boolean getPreviousValue() {
        return getNextValue();
    }
}
