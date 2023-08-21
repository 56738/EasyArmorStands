package me.m56738.easyarmorstands.property.v1_19_4.button;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.item.ItemTemplate;
import me.m56738.easyarmorstands.property.button.ToggleButton;
import org.bukkit.Color;

public class TextBackgroundToggleButton extends ToggleButton<Color> {
    public TextBackgroundToggleButton(Property<Color> property, PropertyContainer container, ItemTemplate item) {
        super(property, container, item);
    }

    @Override
    public Color getNextValue() {
        if (property.getValue() == null) {
            return Color.fromARGB(0, 0, 0, 0);
        } else {
            return null;
        }
    }

    @Override
    public Color getPreviousValue() {
        return getNextValue();
    }
}
