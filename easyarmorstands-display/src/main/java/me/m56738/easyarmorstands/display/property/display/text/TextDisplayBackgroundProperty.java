package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TextDisplayBackgroundProperty implements Property<Optional<Color>> {
    private static final int DEFAULT_COLOR = 0x40000000;
    private final TextDisplay entity;

    public TextDisplayBackgroundProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Optional<Color>> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public @NotNull Optional<Color> getValue() {
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
    public boolean setValue(@NotNull Optional<Color> value) {
        entity.setDefaultBackground(!value.isPresent());
        entity.setBackgroundColor(value.orElse(null));
        return true;
    }
}
