package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;

import java.util.function.Function;

public class BooleanHandler extends ToggleHandler<Boolean> {
    public BooleanHandler(EasyArmorStandsCommon eas, Property<Boolean> property) {
        super(eas, property);
    }

    public static Function<Property<Boolean>, BooleanHandler> provider(EasyArmorStandsCommon eas) {
        return p -> new BooleanHandler(eas, p);
    }

    @Override
    protected boolean toggleNextValue() {
        return property.setValue(!property.getValue());
    }
}
