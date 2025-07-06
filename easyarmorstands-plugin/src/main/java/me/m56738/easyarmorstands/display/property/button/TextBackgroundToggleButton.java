package me.m56738.easyarmorstands.display.property.button;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.property.button.ToggleButton;
import org.bukkit.Color;

import java.util.Optional;

public class TextBackgroundToggleButton extends ToggleButton<Optional<Color>> {
    public TextBackgroundToggleButton(Element element, PropertyType<Optional<Color>> type, SimpleItemTemplate item) {
        super(element, type, item);
    }

    @Override
    public Optional<Color> getNextValue() {
        if (getUntrackedProperty().getValue().isPresent()) {
            return Optional.empty();
        } else {
            return Optional.of(Color.fromARGB(0, 0, 0, 0));
        }
    }

    @Override
    public Optional<Color> getPreviousValue() {
        return getNextValue();
    }
}
