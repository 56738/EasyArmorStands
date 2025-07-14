package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;

import java.util.Optional;

@SuppressWarnings("deprecation") // presence checked in isSupported
public class TextDisplayBackgroundProperty implements Property<Optional<Color>> {
    private static final int DEFAULT_COLOR = 0x40000000;
    private final TextDisplay entity;

    public TextDisplayBackgroundProperty(TextDisplay entity) {
        this.entity = entity;
    }

    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getBackgroundColor");
            TextDisplay.class.getMethod("setBackgroundColor", Color.class);
            TextDisplay.class.getMethod("isDefaultBackground");
            TextDisplay.class.getMethod("setDefaultBackground", boolean.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public PropertyType<Optional<Color>> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public Optional<Color> getValue() {
        if (entity.isDefaultBackground()) {
            return Optional.empty();
        }
        Color color = entity.getBackgroundColor();
        if (color == null) {
            color = Color.WHITE;
        } else if (color.asARGB() == DEFAULT_COLOR) {
            return Optional.empty();
        }
        return Optional.of(color);
    }

    @Override
    public boolean setValue(Optional<Color> value) {
        entity.setDefaultBackground(!value.isPresent());
        entity.setBackgroundColor(value.orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
