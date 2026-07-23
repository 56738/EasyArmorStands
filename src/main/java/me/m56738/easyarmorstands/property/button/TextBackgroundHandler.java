package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.platform.color.ARGBColor;

import java.util.Optional;
import java.util.function.Function;

public class TextBackgroundHandler extends ToggleHandler<Optional<ARGBColor>> {
    public TextBackgroundHandler(EasyArmorStandsCommon eas, Property<Optional<ARGBColor>> property) {
        super(eas, property);
    }

    public static Function<Property<Optional<ARGBColor>>, TextBackgroundHandler> provider(EasyArmorStandsCommon eas) {
        return p -> new TextBackgroundHandler(eas, p);
    }

    @Override
    protected boolean toggleNextValue() {
        if (property.getValue().isPresent()) {
            return property.setValue(Optional.empty());
        } else {
            return property.setValue(Optional.of(ARGBColor.of(0)));
        }
    }
}
