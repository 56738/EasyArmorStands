package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.Color;

import java.util.Optional;

public class TextBackgroundHandler extends ToggleHandler<Optional<Color>> {
    public TextBackgroundHandler(Property<Optional<Color>> property) {
        super(property);
    }

    @Override
    protected boolean toggleNextValue() {
        if (property.getValue().isPresent()) {
            return property.setValue(Optional.empty());
        } else {
            return property.setValue(Optional.of(Color.fromARGB(0, 0, 0, 0)));
        }
    }
}
