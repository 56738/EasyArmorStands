package me.m56738.easyarmorstands.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.platform.color.ARGBColor;
import me.m56738.easyarmorstands.platform.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TextDisplayBackgroundProperty implements Property<Optional<ARGBColor>> {
    private static final int DEFAULT_COLOR = 0x40000000;
    private final TextDisplay entity;

    public TextDisplayBackgroundProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Optional<ARGBColor>> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public @NotNull Optional<ARGBColor> getValue() {
        if (entity.isDefaultBackground()) {
            return Optional.empty();
        }
        ARGBColor color = entity.getBackgroundColor();
        if (color == null) {
            color = ARGBColor.WHITE;
        } else if (color.value() == DEFAULT_COLOR) {
            return Optional.empty();
        }
        return Optional.of(color);
    }

    @Override
    public boolean setValue(@NotNull Optional<ARGBColor> value) {
        entity.setDefaultBackground(value.isEmpty());
        entity.setBackgroundColor(value.orElse(null));
        return true;
    }
}
