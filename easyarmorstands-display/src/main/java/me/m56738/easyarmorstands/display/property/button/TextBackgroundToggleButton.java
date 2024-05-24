package me.m56738.easyarmorstands.display.property.button;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.property.button.ToggleButton;
import org.bukkit.Color;

import java.util.Optional;

public class TextBackgroundToggleButton extends ToggleButton<Optional<Color>> {
    public TextBackgroundToggleButton(Property<Optional<Color>> property, PropertyContainer container, SimpleItemTemplate item) {
        super(property, container, item);
    }

    @Override
    public Optional<Color> getNextValue() {
        if (property.getValue().isEmpty()) {
            return Optional.of(Color.fromARGB(0, 0, 0, 0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Color> getPreviousValue() {
        return getNextValue();
    }
}
