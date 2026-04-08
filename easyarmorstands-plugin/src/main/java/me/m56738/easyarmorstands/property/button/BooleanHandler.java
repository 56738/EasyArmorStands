package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.property.Property;

public class BooleanHandler extends ToggleHandler<Boolean> {
    public BooleanHandler(Property<Boolean> property) {
        super(property);
    }

    @Override
    protected boolean toggleNextValue() {
        return property.setValue(!property.getValue());
    }
}
